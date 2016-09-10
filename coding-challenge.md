#Challenge:

Create a command line program that will take an internet domain name (e.g. “jana.com”) and print out a list of the email addresses that were found on that website. It should find email addresses on any discoverable page of the website, not just the home page.

##Examples:

```
> python find_email_addresses.py jana.com
Found these email addresses:
sales@jana.com
press@jana.com
info@jana.com
...
```

```
> python find_email_addresses.py mit.edu
Found these email addresses:
campus-map@mit.edu
mitgrad@mit.edu
sfs@mit.edu
llwebmaster@ll.mit.edu
webmaster@ll.mit.edu
whatsonyourmind@mit.edu
fac-officers@mit.edu
...
```

##More information:

- You can use any modern programming language you like. We work in Python and Java, so one of those is preferred but not required.
- Your program must work on another computer, so be sure to include any required libraries (using libraries is OK).
- Create a new github repository for this project. The repository should be public but please give it some kind of codename that doesn't have the word `jana` in it. The master branch should be empty, and then create a branch with your code in it.
- Push your branch up to github, and create a pull request.  Send me the link to the pull request, and I can comment directly on it. All our code goes through this code review process, so it's a little glimpse into how we work.
- In your repo, please include a readme that has any instructions we might need to setup and install your solution.

##Style:

- At Jana we follow the Google Style Guides for Python and Java.  However, it is not critical for this challenge.
