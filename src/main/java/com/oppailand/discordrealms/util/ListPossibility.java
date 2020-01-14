package com.oppailand.discordrealms.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ListPossibility<A> extends Possibility<A> {
	private List<A> list = new ArrayList<>();

	public ListPossibility() {

	}

	public ListPossibility(List<A> list) {
		this.list = list;
	}

	@SafeVarargs
	public ListPossibility(A... a) {
		list = Arrays.asList(a);
	}

	public List<A> getList() {
		return list;
	}

	public void setList(List<A> list) {
		this.list = list;
	}

	public void add(A a) {
		list.add(a);
	}

	@Override
	public A getRandom() {
		if (list.isEmpty()) return null;
		if (list.size() == 1) return list.get(0);
		return list.get(new Random().nextInt(list.size()));
	}
}
