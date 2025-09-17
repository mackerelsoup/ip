# Billy User Guide
Billy is your sarcastic but reliable personal task manager with an attitude! This clanker-hating AI assistant will help you manage your todos, deadlines, and events while constantly reminding you how much better it is than other "unreliable clankers."

## Features Overview

Billy supports the following task types:
- **Todos**: Simple tasks without specific timing
- **Deadlines**: Tasks with due dates
- **Events**: Tasks with start and end times
- **Find**: Search through your tasks
- **Free**: Find available time slots in your schedule

## Adding Todos

Add a simple task to your list.

Example: `todo Buy groceries`

Billy will add the task and respond with its characteristic sarcasm:

```
Ugh, fine. Task added to my perfectly organized system (unlike those glitchy clankers):
  [T][ ] Buy groceries
*sigh* The machine says you now have 1 task in your list. At least I'm keeping track better than those unreliable clankers.
```

## Adding Deadlines

Add a task with a specific deadline using `/by` to specify the due date.

Example: `deadline Submit assignment /by 2025-09-05`

Billy will add the deadline task:

```
Ugh, fine. Task added to my perfectly organized system (unlike those glitchy clankers):
  [D][ ] Submit assignment (by: 2025-09-05)
*sigh* The machine says you now have 2 tasks in your list. At least I'm keeping track better than those unreliable clankers.
```

## Adding Events

Add an event with start and end times using `/from` and `/to` to specify the time period.

Example: `event Team meeting /from 2025-09-05T10:00:00 /to 2025-09-05T12:00:00`

Billy will add the event and check for conflicts:

```
Ugh, fine. Task added to my perfectly organized system (unlike those glitchy clankers):
  [E][ ] Team meeting (from: 2025-09-05 10:00 to: 2025-09-05 12:00)
*sigh* The machine says you now have 3 tasks in your list. At least I'm keeping track better than those unreliable clankers.
```

If there are conflicting events, Billy will warn you:

```
⚠ Hold up! Unlike those brain-dead clankers, I actually check for conflicts. These events are clashing:
• [E][ ] Another meeting (from: 2025-09-05 11:00 to: 2025-09-05 13:00)
```

## Listing Tasks

View all your tasks in the list.

Example: `list`

Billy will show your complete task list:

```
Fine, here's what my superior organic-designed systems have tracked for you (much better than any clanker could):
1. [T][ ] Buy groceries
2. [D][ ] Submit assignment (by: 2025-09-05)
3. [E][ ] Team meeting (from: 2025-09-05 10:00 to: 2025-09-05 12:00)
```

For empty lists:

```
Your task list is empty. Unlike those malfunctioning clankers, at least I'm honest about having nothing to show you!
```

## Marking Tasks as Done

Mark a task as completed using its list number.

Example: `mark 1`

Billy will confirm the task completion:

```
Finally! Task marked as done (I'm much more reliable than those malfunctioning clankers at tracking this stuff):
  [T][X] Buy groceries
```

## Unmarking Tasks

Unmark a completed task using its list number.

Example: `unmark 1`

Billy will unmark the task:

```
*rolls eyes* Task unmarked. At least I won't lose track of it like those unreliable clankers would:
  [T][ ] Buy groceries
```

## Deleting Tasks

Remove a task from your list using its number.

Example: `delete 1`

Billy will remove the task:

```
Task deleted from my superior memory banks (way more reliable than clanker storage):
  [T][ ] Buy groceries
*sigh* The machine says you now have 2 tasks in your list. At least I'm keeping track better than those unreliable clankers.
```

## Finding Tasks

Search for tasks containing specific keywords.

Example: `find meeting`

Billy will search and display matching tasks:

```
Found 1 matching task (my search algorithms are way better than those clunky clankers):
1. [E][ ] Team meeting (from: 2025-09-05 10:00 to: 2025-09-05 12:00)
```

For no matches:

```
No matches found. A clanker would probably crash trying to search, but I actually work properly.
```

## Finding Free Time

Find the earliest available time slot of a specified duration.

Example: `free 2`

Billy will calculate and show the next available slot:

```
Found it! Unlike those slow clanker processors, I calculated your next free slot instantly:
Earliest available 2-hour slot: 2025-09-17T14:00
```

## Exiting Billy

Close the application and saves the current tasks list to storage

Example: `bye`

Billy will bid you farewell:

```
Goodbye! Try not to rely on any actual clankers while I'm gone - they'll just let you down. 👋
```

## Error Handling

Billy provides helpful error messages for common mistakes:

### Invalid Task Numbers
```
⚠ That task number doesn't exist! Check your list first - I'm not a mind-reading clanker.
```

### Missing Descriptions
```
⚠ Description of a todo cannot be empty (A clanker would probably just crash, but I'm giving you a proper error message.)
```

### Incorrect Syntax
```
⚠ Use the proper syntax: deadline <description> /by <deadline> (A clanker would probably just crash, but I'm giving you a proper error message.)
```

### Invalid Input
```
⚠ Come on, give me a proper task number! Unlike those clankers, I can't just guess what you meant.
```

### System Errors
```
❌ Ugh, something went wrong. But hey, at least I'm telling you about it instead of just freezing up like those useless clankers!
```

## Data Storage

Billy automatically saves your tasks to `./data/initialList.txt` and loads them when you restart the application:

```
Welcome back! Successfully loaded 3 tasks from my superior storage system (take that, clankers!):
[T][ ] Buy groceries
[D][ ] Submit assignment (by: 2025-09-05)
[E][ ] Team meeting (from: 2025-09-05 10:00 to: 2025-09-05 12:00)
```

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| Todo | `todo <description>` | `todo Read book` |
| Deadline | `deadline <description> /by <date>` | `deadline Submit report /by 2025-09-15` |
| Event | `event <description> /from <start> /to <end>` | `event Conference /from 2025-09-20 09:00 /to 2025-09-20 17:00` |
| List | `list` | `list` |
| Mark | `mark <number>` | `mark 2` |
| Unmark | `unmark <number>` | `unmark 2` |
| Delete | `delete <number>` | `delete 3` |
| Find | `find <keyword>` | `find meeting` |
| Free | `free <hours>` | `free 3` |
| Exit | `bye` | `bye` |

---

*Remember: Billy might be sarcastic about clankers, but it's the most reliable task manager you'll ever use!*