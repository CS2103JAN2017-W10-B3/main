# Script for Manual Testing

This test script is to assist testers in testing `To-do List` by providing them with sample test cases which covers the full range of functionality of `To-do List`.

## Loading sample data

* Before running `toDoList.jar`, copy the `SampleData.xml` into same file location as the jar file
* Rename the `SampleData.xml` file to `toDoList.xml`
* Run `toDoList.jar`

## Preamble

* Definition of tasks:
> * A task having only start time and no end time, or neither start nor end time, is automatically sorted as a floating task.
> * A task having only the end time and no start time is automatically sorted as a deadline task.
> * A task having both the start and end time is automatically sorted as an event task.
> * A task, regardless of the existence of its start and end time, is sorted as a completed time, if it has been flagged as completed before.
> * Notice that completed tasks are not eligible for add command and edit command.

* Definition of task index:
> * Under most circumstances, a task index would be in the format "Task character + index".
> * Task character is one single small letter, with value in either c, d, e, or f, referring to completed, deadline, event and floating respectively.
> * Indexes are unsigned positive numbers.

* Usage of task index
> * Commands including `delete`, `edit`, `select`, `done` would be related to the task indexes. Usually, task indexes are typed in in the format "character + number", for single targeted task. For example: `delete d1` would be referring to deleting deadline task number 1.
> * You have the choice to not including the character signifying the type, and it will by default be recogized as a deadline task.
> * You can also select multiple tasks, by using a dash "-" to specify a range of task index. However, the task character is still compulsory for completed, event and floating tasks, and optional for deadline tasks. For example: `delete d1-3` would be referring to deleting deadline tasks d1, d2 and d3.
> * The multiple selection does not restrict the sequence of index, meaning the starting number could be greater than the ending number. For example: `delete d1-3` would also be referring to deleting deadline tasks d1, d2 and d3.
> * You could have "multiple" multiple selection. For example: `delete d1-3 e2-4` would also be referring to deleting deadline tasks d1, d2 and d3, and event tasks e2, e3 and e4.
> * They are not restricted to be from different types of tasks. For example: `delete d1-3 d4-4` is also fine. If there were any overlaps, no error will be thrown and the union of the two set will be taken.
> * However, we do not support a third multiple selection interval.

## Manual Tests

### Opening the Application

1. Run the .jar file.
2. Wait for a popup from the tray.
3. Press Ctrl+T (Windows users) or Option+T (Mac users) to open. (Can also just click the icon.)
4. Press Ctrl+T/Option+T again to close.

### Adding a floating task

1. Adding a floating task with title only
>  To type: `add CS2103 project`
	> * Added a floating task named 'CS2103 project'
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has neither the start or the end time, hence is treated as a floating task.
	> * Newly added floating task is selected in the floating task list panel

2. Adding a floating task with venue
>  To type: `add CS2102 project /venue home`
	> * Added a floating task named 'CS2103 project' with venue "home"
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has neither the start or the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel

3. Adding a floating task with urgency level
>  To type: `add CS2101 project /level 3`
	> * Added a floating task named 'CS2103 project' with urgency level "3"
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has neither the start or the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel
	> * A red dot is shown on the right hand side of the task pane signifying the level of importance

4. Adding another floating task with lower urgency level
>  To type: `add CS2100 project /level 2`
	> * Added a floating task named "CS2100 project" with urgency level "2"
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has neither the start or the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel
	> * An orange dot is shown on the right hand side of the task pane signifying the level of importance

5. Adding another floating task with description
>  To type: `add CS2010 project /description Very good!`
	> * Added a floating task named 'CS2010 project' with description "Very good!"
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has neither the start or the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel

6. Adding another floating task with starting time using prefix `/from`
>  To type: `add buy lottery /from April 30`
	> * Added a floating task named 'buy lottery' with starting time "April 30 2017 HH:MM" where HH:MM is the current time in the computer system
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has only the start, not the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel

7. Adding another floating task with starting time using prefix `/on`
>  To type: `add buy fruit /on April 29`
	> * Added a floating task named 'buy fruit' with starting time "April 29 2017 HH:MM" where HH:MM is the current time in the computer system
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has only the start, not the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel

8. Adding another floating task with tag
>  To type: `add buy stationary #important #necessary`
	> * Added a floating task named 'buy stationary' with tags "important" and "necessary"
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has only the start, not the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel

9.  Undo recently added task
>  To type: `undo`
	> * The most recently added task "buy stationary" with tags "important" and "necessary" would be removed.

10.  Undo the next recently added task
>  To type: `undo`
	> * The second most recently added task "buy fruit" with start time "April 29 2017 HH:MM" would be removed.

### Adding a deadline task

1. Adding a deadline task with title and end time only, using the prefix `/by`.
>  To type: `add MA1101R assignment /by April 19 23:59`
	> * Added a deadline task named 'MA1101R assignment' with ending time "April 19 2017 23:59"
	> * Newly added task is selected in the deadline task list panel

2. Adding a deadline task with title, venue and end time only.
>  To type: `add MA2101 assignment /venue Science /by April 18 23:59`
	> * Added a deadline task named 'MA2101 assignment' with venue "Science" and ending time "April 18 2017 23:59"
	> * Newly added task is selected in the deadline task list panel
	> * The newly added task is ranked above the task "MA1101R assignment" added in the previous operation, as the deadline tasks are sorted primarily according to the due date and time.

3.  Adding a deadline task with title, urgency level and end time only.
>  To type: `add MA2216 assignment /level 3 /by April 18 23:59`
	> * Added a deadline task named 'MA2216 assignment' with urgency level "3" and ending time "April 18 2017 23:59"
	> * Newly added task is selected in the deadline task list panel
	> * The newly added task is ranked above the task "MA2101 assignment" added just now, as the deadline tasks are sorted primarily according to due date and time, and then by urgency level assigned within if tasks are having the same deadline.

4.  Adding a deadline task with title, description and end time only.
>  To type: `add MA3220 assignment /description 20% of grade /by April 17 23:59`
	> * Added a deadline task named 'MA3220 assignment' with description "20% of grade" and ending time "April 17 2017 23:59"
	> * Newly added task is selected in the deadline task list panel

5.  Adding a deadline task with title, tags and end time only.
>  To type: `add MA3110 assignment #math #homework /by April 17 23:59`
	> * Added a deadline task named 'MA3110 assignment' with tags "math" and "homework" and ending time "April 17 2017 23:59"
	> * Newly added task is selected in the deadline task list panel

### Adding an event task

1.  Adding an event task with title, start time and end time only, using the prefix `/from` for start time and `/to` for end time.
>  To type: `add ST1131 exam /from April 21 9:00 /to April 21 11:00`
	> * Added an event task named 'ST1131 exam' with start time "April 21 2017 9:00" and end time "April 21 2017 11:00"
	> * Newly added task is selected in the event task list panel

2.  Adding an event task with title, venue, start time and end time only
>  To type: `add ST2334 exam /venue MPSH2 /from April 22 9:00 /to April 22 11:00`
	> * Added an event task named 'ST2334 exam' with venue at "MPSH2", start time "April 22 2017 9:00" and end time "April 22 2017 11:00"
	> * Newly added task is selected in the event task list panel
	> * The newly added task is ranked below the previously added task "ST1131 exam" since it has a starting time later than the previous task.

3.  Adding an event task with title, description, start time and end time only
>  To type: `add ST3131 exam /description 60% of grade /from April 25 9:00 /to April 22 11:00`
	> * Added an event task named 'ST3131 exam' with start time "April 25 2017 9:00", end time "April 25 2017 11:00" and description "60% of grade".
	> * Newly added task is selected in the event task list panel

4.  Adding an event task with title, urgency level, start time and end time only
>  To type: `add ST3131 exam /from April 25 9:00 /to April 25 11:00 /level 1`
	> * Added an event task named 'ST3131 exam' with urgency level "1", start time "April 25 2017 9:00" and end time "April 25 2017 11:00"
	> * Newly added task is selected in the event task list panel
	> * On the task card there is a yellow dot signifying the level of urgency is at 1.

5.  Adding an event task with title, tags, start time and end time only
>  To type: `add ST3247 exam /from April 27 9:00 /to April 27 11:00 #exam #important`
	> * Added an event task named 'ST3131 exam' with tags "exam" and "important", start time "April 25 2017 9:00" and end time "April 25 2017 11:00"
	> * Newly added task is selected in the event task list panel

### Selecting tasks

1.  Select an event task with keyword "select"
>  To type: `select e1`
	> * The event task with index "e1" will be selected and highlighted in grey in the event task panel

2.  Select a deadline task with keyword "select"
>  To type: `select d1`
	> * The deadline task with index "d1" will be selected and highlighted in grey in the deadline task panel

3.  Select a floating with keyword "select"
>  To type: `select f1`
	> * The floating task with index "f1" will be selected and highlighted in grey in the floating task panel

4.  Select an event task without keyword "select"
>  To type: `e1`
	> * The event task with index "e1" will be selected and highlighted in grey in the event task panel

5.  Select a deadline task without keyword "select"
>  To type: `d1`
	> * The deadline task with index "d1" will be selected and highlighted in grey in the deadline task panel

6.  Select a floating task without keyword "select"
>  To type: `f1`
	> * The floating task with index "f1" will be selected and highlighted in grey in the floating task panel

7.  Select multiple floating tasks without keyword "select"
>  To type: `f1-3`
	> * The floating tasks with index f1, f2 and f3 will be selected and highlighted in grey in the floating task panel

8.  Select multiple floating tasks without keyword "select", with the number sequence inverted
>  To type: `e4-2`
	> * The floating tasks with index e4, e3 and e2 will be selected and highlighted in grey in the floating task panel

9.  Select multiple floating tasks without keyword "select", with the number sequence inverted, and without specifying the task
>  To type: `4-2`
	> * The deadline tasks with index d4, d3 and d2 will be selected and highlighted in grey in the deadline task panel

### Editing tasks

1.  Editing task title with task index specified in the command
>  To type: `edit f1 /title sleeping`
	> * The floating task with index "f1" has its title changed to "sleeping"
	> * The edited task is selected in the floating task panel and highlighted as grey

2.  Editing floating task start time with task index specified in the command
>  To type: `edit f1 /from April 19 8:00pm`
	> * The floating task with index "f1" has its start time changed to "April 19 2017 8:00pm"
	> * The edited task is selected in the floating task panel and highlighted as grey
	> * If the task has no start time specified previously, its order in the list might change since floating tasks are sorted according to its urgency level and then start time.

3.  Editing floating task end time with task index specified in the command
>  To type: `edit f1 /to April 19 10:00pm`
	> * The floating task with index "f1" has added a new parameter "end time" with value "April 19 10:00pm".
	> * The edited task is selected in the event task panel and highlighted as grey
	> * Since it is previously having no end time and sorted as floating task, adding the end time parameter changes its task type to an event task, and hence is no longer listed in the floating task panel.

4.  Editing deadline task venue with task index specified in the command
>  To type: `edit d1 /venue Bus stop`
	> * The deadline task with index "d1" has its venue updated to having value "Bus stop", or the venue parameter is added if it does not originally have one.
	> * The edited task is selected in the deadline task panel and highlighted as grey.

5.  Editing deadline task venue with task index specified in the command
>  To type: `edit d1 /description I don't know`
	> * The deadline task with index "d1" has its description updated to having value "I don't know", or the description parameter is added if it does not originally have one.
	> * The edited task is selected in the deadline task panel and highlighted as grey.

6.  Editing deadline task urgency level with task index specified in the command
>  To type: `edit d1 /level 1`
	> * The deadline task with index "d1" has its urgency level updated to having value "1", or the urgency level parameter is added if it does not originally have one.
	> * The edited task is selected in the deadline task panel and highlighted as grey.

7.  Editing deadline task tag with task index specified in the command
>  To type: `edit d1 #important`
	> * The deadline task with index "d1" has its tags overwritten and updated to having value "important", or the tag "important" is added if it does not originally have tags.
	> * The edited task is selected in the deadline task panel and highlighted as grey.

8.  Editing event task tag after selecting it beforehand
>  To type: `e1` followed by `edit /title Go to school`
	> * The event task with index "e1" is selected in event task panel after the first command, then the title of the task is updated to become "Go to school" after the second command.
	> * The edited task is selected in the event task panel and highlighted as grey.

9.  Editing multiple event tasks after selecting them beforehand
>  To type: `e1-3` followed by `edit /venue SoC`
	> * The event tasks with indexes e1, e2 and e3 are selected in event task panel after the first command, then the venues of these tasks are updated to become "SoC" after the second command.
	> * The edited tasks are selected in the event task panel and highlighted as grey.

10.  Editing multiple deadline tasks tags after selecting them beforehand
>  To type: `9-6` followed by `edit  #school`
	> * The deadline tasks with indexes d6, d7, d8 and d9 are selected in deadline task panel after the first command, then the tags of the task are updated to having only #school after the second command.
	> * The edited tasks are selected in the deadline task panel and highlighted as grey.

11.  Deleting a the start time parameter from an event task
>  To type: `delete e1 /from`
	> * The event task with index has its start time reset to null. Notice that such changes causes a change in task category also.
	> * Thus the new task is displayed and highlighted in grey in the deadline task list, since it is now fitting under the definition for deadline task.

12.  Deleting the venue parameter from a floating task
>  To type: `delete f1 /venue`
	> * The floating task with the index f1 will have its venue value deleted and reset to null, if there were any.
	> * Notice that if the task does not primarily have a venue value, nothing will be changed
	> * In anyway, the task with the parameter deleted will be highlighted in grey and selected in the floating task list.

13.  Deleting the description parameter from a task after selecting it beforehand
>  To type: `d1` followed by `delete /description`
	> * The deadline task with the index d1 will have its description value deleted and reset to null, if there were any.
	> * In anyway, the task with the parameter deleted will be highlighted in grey and selected in the deadline task list.

14.  Deleting the urgency level parameter from multiple tasks after selecting them beforehand
>  To type: `e2-4` followed by `delete /level`
	> * The event tasks with the indexes e2, e3 and e4 will have their urgency level values deleted and reset to null, if there were any.
	> * In anyway, the task with the parameter deleted will be highlighted in grey and selected in the event task list.

14.  Deleting the tag parameter from multiple tasks
>  To type: `delete 5-3 #`
	> * The deadline tasks with the indexes d3, d4 and d5 will have their tags deleted and reset to null, if there were any.
	> * The hash sign is the prefix for tags to signify that tags are to be deleted.
	> * In anyway, the task with the parameter deleted will be highlighted in grey and selected in the deadline task list.

### Deleting tasks

1.  Deleting task
>  To type: `delete f1 `
	> * The floating task with index "f1" is deleted
	> * The task is deleted from the floating task panel.

2.  Deleting multiple tasks
>  To type: `delete f1-3 `
	> * The floating tasks with indexes f1, f2 and f3 are deleted
	> * The tasks are deleted from the floating task panel.

3.  Deleting multiple deadline tasks (not compulsory to specify the task character)
>  To type: `delete 1-3 `
	> * The deadline tasks with indexes d1, d2 and d3 are deleted
	> * The tasks are deleted from the deadline task panel.

### Finding tasks

1.  Find tasks with keyword
>  To type: `find exam`
	> * All 3 panels will show shortlisted tasks that contains keywords in either titles, venues, start time, end time, descriptions, and tags.
	> * Only the shortlisted tasks are shown in their respective task list panels.
	> * The total number of tasks will be shown in the command result window.

### Listing tasks

1.  List all tasks
>  To type: `list`
	> * All tasks are listed out in their repsective panels.

2.  List all tasks that falls under a certain range of time
>  To type: `list /from Today /to Tomorrow`
	> * All tasks that fulfil the time comparison criteria will be listed in their respective task lists.
	> * For a floating task, if its start time is after "Today" and before "Tomorrow", it will be listed. Otherwise, it is not listed.
	> * For a deadline task, if its deadline is after "Today" and before "Tomorrow", it will be listed. Otherwise, it is not listed.
	> * For a event task, if its start time is after "Today" and end time before "Tomorrow", it will be listed. Otherwise, it is not listed.
	> * You can use `/by` instead of `/to`.
	> * Notice that this listing function is primarily for user to look up for tasks in a period that lasts a certain days. Hence no matter how accurate the time input is, all tasks on the same day as the start or end boundary will also be listed.

3. List all tasks that falls on a certain day
>  To type: `list /on Today`
	> * All tasks that have either start time or end time parameter present and falling on "Today" will be listed, no matter their types.

### Clearing of tasks

1.  Clear all tasks
>  To type: `clear`
	> * All tasks are cleared and the entire list is now empty.

### Undo commands

1.  Undo the most recent commands
>  To type: `undo`
	> * The entire to-do list is restored to its most recent state before the previous undo-able command.
	> * You can maximum undo for 3 times.
	> * The undo-able commands are: add, edit, complete (done), clear and delete.

### Completing tasks

1.  Completing task
>  To type: `done f1 `
	> * The floating task with index "f1" is marked as completed.
	> * The task is deleted from the floating task panel and shown in the completed list.
	> * It is selected and highlighted in grey.

2.  Completing multiple tasks
>  To type: `done 1-3 `
	> * The floating tasks with indexes d1, d2 and d3 are marked as completed
	> * The tasks are deleted from the deadline task panel and shown in the completed list.
	> * The completed tasks selected and highlighted in grey.

### Viewing help

1.  View command instruction for a command
>  To type: `help add`
	> * The usage instruction of add command is shown in the command result window.

### Save to a directory

1.  Save the to do list to a stated directory
>  To type: `save E:/my folder`
	> * The xml file with the necessary data of the to-do list is stored to the directory.

2.  Save the to do list to the default directory
>  To type: `save`
	> * The xml file with the necessary data of the to-do list is stored to the data folder in the repo folder by default.

### Change the default directory

1.  Change the default directory to the stated directory
>  To type: `changedir E:/my folder`
	> * A .xml will be saved at the same time as stating the changing of directory.
	> * Any future saving using the command word `key` only will be saved to this directory.

### Import a saved file from a specified file path

1.  import the saved file into the to-do list
>  To type: `import E:/my folder/todoList`
	> * You may also typed the file format, i.e. `/todoList.xml`
	> * The .xml file will be imported if it exists in the folder and has the file name.

### Controlling UI

1.  Scroll down a specific screen
>  Keys to press: `ctrl(control) + alt(option) + C`
	> * The completed task list will be scrolled down
	> * You may try D for deadline task list, E for event task list, F for floating task list.

2.  Scroll up a specific screen
>  Keys to press: `ctrl(control) + shift + F`
	> * The floating task list will be scrolled up
	> * You may try C for completed task list, D for deadline task list and E for event task list.

### Auto-complete

1.  View the 10 most recent successful commands that you have typed
>  Key to press: `down`
	> * A list of historical commands is shown beneath the command line.
	> * You may continue to press down or up and choose the desired command to be re-typed into the command line. Press enter and the command will appear in the command line. Notice that it will overwrite what you have previously typed.

2.  Auto-completion of task command and prefixes
>  To type: `ad`
	> * A tab with the word "add" is shown beneath the command line. You may continue to press down and enter to type the word into the command line.
	> * You may also try typing "/" and all the task parameter prefixes will appear. This is especially useful when you do not want to type long prefixes, such as `/description`.

### Viewing joke

1.  Type out "joke"
>  To type: `joke`
	> * A joke of probably one or two lines is shown in the command result panel. :D

