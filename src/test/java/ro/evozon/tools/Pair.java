package ro.evozon.tools;

public class Pair<R, L> {

	private R left;
	private L right;

	public static <K, V> Pair<K, V> of(K k, V v) {
		return new Pair<K, V>(k, v);
	}

	public Pair() {
	}

	public Pair(R key, L value) {
		this.left(key);
		this.right(value);
	}

	public R left() {
		return left;
	}

	public Pair<R, L> left(R key) {
		this.left = key;
		return this;
	}

	public L right() {
		return right;
	}

	public Pair<R, L> right(L value) {
		this.right = value;
		return this;
	}

	public <T> Pair<L,R> makePair() {
		return new Pair<L,R>();
	}
}