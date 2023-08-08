package weixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List temp = new ArrayList<String>();
		temp.add("aaa");
		temp.add("bbb");
		Object value;
		// TODO
		value = temp;
		Collection collection = (Collection) value;
		value = (collection.isEmpty() ? null : collection.iterator().next());
		System.out.println(value);
	}

}
