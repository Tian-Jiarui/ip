import java.util.Scanner;

public class Valencia {
    public static void main(String[] args) {
        String line = "_________________________________________________\n";
        String welcomeGreet = "Hello! I'm Valencia.\n"
                + "What can I do for you?\n";
        String exitGreet = "Bye. Hope to see you again soon!\n";

        Scanner sc = new Scanner(System.in);
        Storage storage = new Storage("data/valencia.txt");
        TaskList taskList = storage.load();
        System.out.println(String.format("%s%s%s", line, welcomeGreet, line));

        while (true) {
            String input = sc.nextLine().trim();

            try {
                if (input.toLowerCase().startsWith("mark")) {
                    int taskNum;
                    try {
                        taskNum = Integer.parseInt(input.substring(4).trim());
                    } catch (NumberFormatException e) {
                        throw new ValenciaException("Sorry! I need a task number!");
                    }
                    if (taskNum < 1 || taskNum > taskList.size()) {
                        throw new ValenciaException("That task number do not exist :P");
                    }

                    taskList.markDone(taskNum - 1);
                    storage.save(taskList);
                    System.out.println(String.format("Nice! I've marked this task as done:\n %s", taskList.get(taskNum - 1)));
                    continue;

                } else if (input.toLowerCase().startsWith("unmark")) {
                    int taskNum;
                    try {
                        taskNum = Integer.parseInt(input.substring(6).trim());
                    } catch (NumberFormatException e) {
                        throw new ValenciaException("Sorry! I need a task number!");
                    }
                    if (taskNum < 1 || taskNum > taskList.size()) {
                        throw new ValenciaException("That task number do not exist :P");
                    }
                    taskList.unmarkDone(taskNum - 1);
                    storage.save(taskList);
                    System.out.println(String.format("OK, I've marked this task as not done yet:\n %s", taskList.get(taskNum - 1)));
                    continue;

                } else if (input.toLowerCase().startsWith("todo")) {
                    String desc = input.substring(4).trim();
                    if (desc.isEmpty()) {
                        throw new ValenciaException("I did not receive a task :(");
                    }
                    Task todoTask = new Todo(desc);
                    taskList.add(todoTask);
                    storage.save(taskList);
                    System.out.println(String.format("Got it. I've added this task:\n %s\nNow you have %s tasks in the list", todoTask, taskList.size()));
                    continue;

                } else if (input.toLowerCase().startsWith("deadline")) {
                    String desc = input.substring(8).trim();
                    String[] parts = desc.split(" /by ", 2);
                    if (parts.length < 2) {
                        throw new ValenciaException("Wrong deadline format!");
                    }
                    String input1 = parts[0].trim();
                    String input2 = parts[1].trim();
                    Task deadlineTask = new Deadline(input1, input2);
                    taskList.add(deadlineTask);
                    storage.save(taskList);
                    System.out.println(String.format("Got it. I've added this task:\n %s\nNow you have %s tasks in the list.", deadlineTask, taskList.size()));
                    continue;


                } else if (input.toLowerCase().startsWith("event")) {
                    String desc = input.substring(5).trim();
                    String[] firstPart = desc.split(" /from ", 2);
                    if (firstPart.length < 2) {
                        throw new ValenciaException("Wrong event format!");
                    }
                    String input1 = firstPart[0].trim();
                    String[] secondPart = firstPart[1].split(" /to ", 2);
                    if (secondPart.length < 2) {
                        throw new ValenciaException("Wrong event format!");
                    }
                    String input2 = secondPart[0].trim();
                    String input3 = secondPart[1].trim();
                    Task eventTask = new Event(input1, input2, input3);
                    taskList.add(eventTask);
                    System.out.println(String.format("Got it. I've added this task:\n %s\n Now you have %s in the list.", eventTask, taskList.size()));
                    continue;

                } else if (input.toLowerCase().startsWith("delete")) {
                    String desc = input.substring(6).trim();
                    if (desc.isEmpty()) {
                        throw new ValenciaException("Please give me something to delete!");
                    }
                    int taskNum;
                    try {
                        taskNum = Integer.parseInt(input.substring(6).trim());
                    } catch (NumberFormatException e) {
                        throw new ValenciaException("I need a task number!");
                    }
                    if (taskNum > taskList.size()) {
                        throw new ValenciaException("This task doesn't exit :(");
                    } else if (taskNum < 1) {
                        throw new ValenciaException("There is no tasks to delete :(");
                    }
                    Task removedTask = taskList.remove(taskNum - 1);
                    storage.save(taskList);
                    System.out.println(String.format("Noted. I've removed this task:\n %s\n Now you have %s tasks in the list.", removedTask, taskList.size()));
                    continue;
                }

                switch (input.toLowerCase()) {
                    case "list":
                        System.out.println(String.format("Here are the tasks in your list:\n"));
                        taskList.printList();
                        break;
                    case "bye":
                        System.out.println(String.format("%s%s%s", line, exitGreet, line));
                        sc.close();
                        return;
                    default:
                        throw new ValenciaException("I do not understand what you are saying :'(");
                }
            } catch (ValenciaException e) {
                System.out.println(String.format("%s\n%s\n%s", line, e.getMessage(), line));
            }
        }
    }

    private static int parseTaskNumber(String input, String commandWord) {
        String rest = input.substring(commandWord.length()).trim();
        try {
            return Integer.parseInt(rest);
        } catch (NumberFormatException e) {
            throw new ValenciaException("Sorry! I need a task number!");
        }
    }

    private static void validateTaskNumber(int taskNum, TaskList taskList) {
        if (taskNum < 1 || taskNum > taskList.size()) {
            throw new ValenciaException("That task number does not exist :P");
        }
    }
}

