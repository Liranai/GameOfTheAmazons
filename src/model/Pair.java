package model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<T1, T2>  implements Serializable {

	private static final long serialVersionUID = -674466318441229780L;
	private T1 first;
	private T2 second;

	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s)", first.toString(), second.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (first.equals(((Pair<T1,T2>)obj).getFirst()) && second.equals(((Pair<T1,T2>)obj).getSecond()))
			return true;
		return false;
	}
}
