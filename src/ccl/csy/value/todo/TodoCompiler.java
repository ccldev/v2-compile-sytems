package ccl.csy.value.todo;

public class TodoCompiler {

	public static String compileTodo(String todo) {
		TodoType type = TodoType.get(todo.charAt(0));
		TodoInfo info = TodoInfo.make(type, todo);
		
		StringBuilder builder = new StringBuilder();
		builder.append(info.getBase());
		
		String next = info.getTodo().trim();
		if(!next.isEmpty()){
			builder.append("\n");
			builder.append(compileTodo(next));
		}
		
		return builder.toString();
	}

}
