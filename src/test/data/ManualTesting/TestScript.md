# Script for Manual Testing

This test script is to assist testers in testing `To-do List` by providing them with sample test cases which covers the full range of functionality of `To-do List`.

## Loading sample data

* Before running `toDoList.jar`, copy the `SampleData.xml` into same file location as the jar file
* Rename the `SampleData.xml` file to `toDoList.xml`
* Run `toDoList.jar`

## Manual Tests

### Adding a task
1. Adding a floating task
>  To type: `add CS2103 project` 
	> * Added a floating task named 'CS2103 project'
	> * Newly added floating task is selected in the floating task list panel

2. Adding a floating task with venue
>  To type: `add CS2102 project /venue home` 
	> * Added a floating task named 'CS2103 project' with venue "home"
	> * Newly added task is selected in the floating task list panel 
	
3. Adding a floating task with urgency level
>  To type: `add CS2101 project /level 3` 
	> * Added a floating task named 'CS2103 project' with urgency level "3"
	> * Newly added task is selected in the floating task list panel 
	> * A red dot is shown on the right hand side of the task pane signifying the level of importance

4. Adding another floating task with lower urgency level
>  To type: `add CS2100 project /level 2`
	> * Added a floating task named "CS2100 project" with urgency level "2"
	> * Newly added task is selected in the floating task list panel
	> * An orange dot is shown on the right hand side of the task pane signifying the level of importance

