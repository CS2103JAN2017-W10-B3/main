# Script for Manual Testing

This test script is to assist testers in testing `To-do List` by providing them with sample test cases which covers the full range of functionality of `To-do List`.

## Loading sample data

* Before running `toDoList.jar`, copy the `SampleData.xml` into same file location as the jar file
* Rename the `SampleData.xml` file to `toDoList.xml`
* Run `toDoList.jar`

## Manual Tests

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
	
7. Adding another floating task with tag
>  To type: `add buy stationary #important #necessary` 
	> * Added a floating task named 'buy stationary' with tags "important" and "necessary"
	> * Task type is automatically assigned according to the existence of starting and ending time. Here it has only the start, not the end time, hence is treated as a floating task.
	> * Newly added task is selected in the floating task list panel
	
8.  Undo recently added task
>  To type: `undo`
	> * The most recently added task "buy stationary" with tags "important" and "necessary" would be removed. 
	
9.  Undo the next recently added task
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
	> * The deadline task with index "e1" will be selected and highlighted in grey in the deadline task panel
	
6.  Select a floating task without keyword "select"
>  To type: `f1`
	> * The floating task with index "e1" will be selected and highlighted in grey in the floating task panel

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
	> * The edited task is selected in the deadline task panel and highlighted as grey

5.  Editing deadline task venue with task index specified in the command
>  To type: `edit d1 /description I don't know`
	> * The deadline task with index "d1" has its description updated to having value "I don't know", or the description parameter is added if it does not originally have one.
	> * The edited task is selected in the deadline task panel and highlighted as grey
	
6.  Editing deadline task urgency level with task index specified in the command
>  To type: `edit d1 /level 1`
	> * The deadline task with index "d1" has its urgency level updated to having value "1", or the urgency level parameter is added if it does not originally have one.
	> * The edited task is selected in the deadline task panel and highlighted as grey
	
7.  Editing deadline task tag with task index specified in the command
>  To type: `edit d1 #important`
	> * The deadline task with index "d1" has its tags overwritten and updated to having value "important", or the tag "important" is added if it does not originally have tags.
	> * The edited task is selected in the deadline task panel and highlighted as grey
	
8.  Editing event task tag after selecting it beforehand
>  To type: `e1` followed by `edit /title Go to school`
	> * The event task with index "e1" is selected in event task panel after the first command, then the title of the task is updated to become "Go to school" after the second command.
	> * The edited task is selected in the deadline task panel and highlighted as grey
	
### Deleting tasks

### Finding tasks

1.  Find tasks with keyword
>  To type: `find exam`
	> * All 3 panels will show shortlisted tasks that contains keywords in either titles, venues, start time, end time, descriptions, and tags.

### Listing tasks

### Completing tasks

### Changing save location

### Viewing help

### Controlling UI

### Auto-complete

### Clearing of tasks

### Viewing joke

### 
