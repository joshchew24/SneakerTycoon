# Sneaker Tycoon

## Start your Reselling Business

*Sneaker Tycoon* is a clicker tycoon game in which you resell shoes in order to earn money to **upgrade your reselling
business.** Click to earn money, buy and sell shoes for profit, and watch your wealth grow!

You can spend your money on:
- buying and selling even more sneakers
- buying special sneakers for **powerful bonuses**
- upgrading your clicking power to earn money faster

Your final goal is to earn $1,000,000 so that you can expand your business and open a physical store!

This game is for all audiences, but is most appealing to people who are looking to play something casually to pass 
the time, and people who like sneakers and want to simulate a reselling business without spending real money and
taking real risks. 

I chose this project because I love sneakers and I love playing games, so I wanted to create something that combines 
these passions. Growing up, I loved playing games like Roller Coaster Tycoon, Zoo Tycoon, and Game Dev Story. Sneaker
Tycoon takes inspiration from these games, and features some of the community's most loved sneakers across 
generations. 

## User Stories
- As a user, I want to be able to add sneakers to my personal collection.
- As a user, I want to be able to sell my sneakers when it is profitable.
- As a user, I want to be able to upgrade the amount of money I can earn per click.
- As a user, I want a simulated marketplace with realistic listings.

## Phase 2 User Stories
- As a user, I want to be able to save my game progress to a file
- As a user, I want to be able to load my game progress from a file

## Phase 4: Task 2
Type Hierarchy:
CollectionMenu, DeveloperMenu, MarketplaceMenu, UpgradeMenu, and SneakerTycoonApp all extend the abstract Menu class, 
and each have distinct implementations of displayMenu() and processMenuCommand().

## Phase 4: Task 3
Looking back on my design of this program, the first thing I notice is that there is a significant amount of coupling 
between the GUI classes and the model classes. If I were to refactor the design of the project, here are some changes I 
would make.
- create a class in the model package representing the "game"
- refactor the GUI to interact with the model solely through a single "game" object
  - this removes the need for multiple associations from different GUI classes to Business and Sneaker

Throughout the process of creating this program, we learned things in class that I realized could be applied to improve
my project. 
- refactor any "transactional" behaviour to throw exceptions instead of only returning booleans
- implement proper bidirectional associations in the GUI classes 
  - OR apply the Observer design pattern, which is probably better
  
