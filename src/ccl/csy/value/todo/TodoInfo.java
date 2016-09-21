package ccl.csy.value.todo;

import java.util.HashMap;

public abstract class TodoInfo {

	private static HashMap<TodoType, TodoInfo> map = new HashMap<>();
	
	public static TodoInfo make(TodoType type, String todo) {
		return map.get(type).init(todo);
	}
	public static void add(TodoType type, TodoInfo info){
		map.put(type, info);
	}
	
	public abstract TodoInfo init(String todo);

	public abstract String getBase();
	public abstract String getTodo();

}
