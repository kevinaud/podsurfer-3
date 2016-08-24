# PodSurfer Template Project

## Using Git
For this project, you will need to manage your project using Git. Git is a version control system that is used for software development and other version control tasks. As a distributed revision control system it is aimed at speed, data integrity, and support for distributed, non-linear workflows.

Git allows you to work on the project in parallel with your fellow team members. If you both change the same file, you can merge your changes together. Git also allows you to view the history of your code, undo changes, manage multiple branches of work, and more, all from the command line. It also allows you to access your code from any computer!

If you are unfamiliar with Git, here is a quick resource for learning the commands you'll need:
`https://git-scm.com/docs/gittutorial`

If you want to know everything there is to know about Git, here is *the* book:
`https://git-scm.com/book/en/v2`

For this project, we expect all team members to be contributing to their codebase, and using Git. Whenever you work on the project, you should pull any changes before starting. When you have finished something (and nothing is in a broken state!!), you should commit your code, handle any merge conflicts, and push it to the repository. If you are working on something and it is in an unfinished or broken state, you should never push it to the master branch. Create your own branch and push it there.

## Deployments
For this project, we expect you to have continuous integration setup. This means, when you make a commit to your master branch, it will automatically trigger a build in Codeship. Codeship should run all of your tests, and if they pass, allow the code to be deployed to Heroku.

### Codeship
`https://codeship.com/`
*Codeship* is a hosted continuous delivery service that focuses on speed, reliability and simplicity. You configure Codeship to build and deploy your application from GitHub to the staging or the production platform of your choice. The service offers a variety of powerful setup options.

### Heroku
`https://www.heroku.com/`
*Heroku* is a cloud platform that lets companies build, deliver, monitor and scale apps. When you deploy a Heroku app, it is deployed to a live website. If you don't already have a personal domain, it will host it for you. This is what Credera is using for the PodSurfer API you will be using for this project.

## Postman
`https://www.getpostman.com/`
Postman is a Google Chrome application that allows you to create and send any HTTP request. You can use it to play around with the PodSurfer API, and test requests and see what the responses look like. When using Postman, you are making actual requests, so if you send a successful request that creates a new podcast, that new podcast will now be in the PodSurfer database. Make sure you send your requests to *your team's* PodSurfer API! Insert your team number in place of the '#' in this url: `https://podsurfer-#.herokuapp.com/`
