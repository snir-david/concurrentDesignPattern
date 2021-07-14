import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class A2018Property<V> extends Observable implements Observer {
    private V value;
    private List<Runnable> onChangeList;
    private BinaryOperator<V> binaryOperator;

    public A2018Property() {
        onChangeList = new ArrayList<>();
        binaryOperator = (x, y) -> (y);
    }

    public V get() {
        return value;
    }

    public void set(V v) {
        if (value != v) {
            value = v;
            setChanged();
            notifyObservers();
            for (Runnable r : onChangeList) {
                r.run();
            }
        }
    }

    public void bindTo(A2018Property<V> prop) {
        prop.addObserver(this);
    }

    public void bindBi(A2018Property<V> prop) {
        bindTo(prop);
        addObserver(prop);
    }

    public void addOnChange(Runnable r) {
        onChangeList.add(r);
    }

    public void setChangeFunc(BinaryOperator<V> b) {
        binaryOperator = b;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof A2018Property) {
            A2018Property<V> v = (A2018Property<V>) o;
            set(binaryOperator.apply(value, v.get()));
        }
    }

    public static void main(String[] args) {
        A2018Property<Integer> p = new A2018Property<>(); // we can choose any inner type
        p.set(5);
        Integer x = p.get(); // x is 5;
        A2018Property<Integer> p1 = new A2018Property<>(), p2 = new A2018Property<>();
        p.bindTo(p1); // when p1 changes so does p
        p2.bindBi(p1); // when either one changes so does the other
        p2.set(10); // now p1’s value is 10 and hence p’s value is also 10
        // we can also set any change function we like, for example:
        // given the old value, and the new value, return and set their average
        p.setChangeFunc((oldVal, newVal) -> (oldVal + newVal) / 2);
        p1.set(20);
        x = p.get(); // x is (10+20)/2 = 15;
        // we can also add event handlers of the form “void f();”
        // these event handlers will be invoked by any change to the property
        p.addOnChange( ()->System.out.println("p has Changed!"));
        p.addOnChange(() -> System.out.println("I was invoked as well"));
        p.set(20); // directly puts 20
        x = p.get(); // x is 20, set() does not invoke the change function
        // but p is indeed changed and thus the output will be:
        // p has Changed!
        // I was invoked as well
    }
}
