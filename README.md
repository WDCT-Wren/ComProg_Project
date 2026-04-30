# Computer Programming 2 Final Project

## Overview
This is a sample "alpha" test for Group 1's 2d <b>Space Impact</b> style arcade game that using 
<b>JavaFX</b> with the addition of <b>JavaFX's FXGL 2D Game Engine</b>.

### Controls

| Key         | Action |
|-------------|--------|
| `W A S D`   | Move |
| `Space Bar` | Shoot |
| `ESC`       | Pause |
| `Q`         | Fire Bullet|
| `E`         | Ice Bullet |

### Project Structure
```
├── src/
│   └── main/
│       ├──java/
│       │   ├── org.group1.GamePackage/
│       │   │    ├── Application                   # Main entry point
│       │   │    ├── Components/                   # Entity components (behavior, animation, timers)
│       │   │    │   ├── Enemy/
│       │   │    │   │   ├── BossComponent
│       │   │    │   │   ├── EnemyAnimationComponent
│       │   │    │   │   ├── EnemyDropsAnimationComponent
│       │   │    │   │   └── MiniBossComponent
│       │   │    │   ├── Player/
│       │   │    │   │   └── PlayerComponent
│       │   │    │   ├── PowerUps/
│       │   │    │   │   ├── BoostUpComponent
│       │   │    │   │   ├── FirePowerUpComponent
│       │   │    │   │   └── IcePowerUpComponent
│       │   │    │   ├── Projectiles/
│       │   │    │   │   ├── BulletAnimationComponent
│       │   │    │   │   └── LaserAnimationComponent
│       │   │    │   └── UI/
│       │   │    │       ├── GameOverComponent
│       │   │    │       ├── OverlayAnimationComponent
│       │   │    │       ├── SwitchBulletAnimationComponent
│       │   │    │       └── TimerComponent
│       │   │    ├── Factory/                        # Entity and scene factories
│       │   │    │   ├── BackgroundFactory
│       │   │    │   ├── BossFactory
│       │   │    │   ├── EntityFactory
│       │   │    │   ├── InstructionPageFactory 
│       │   │    │   ├── InterfaceFactory
│       │   │    │   └── MainSceneFactory
│       │   │    ├── Handlers/                       # Game logic and input management
│       │   │    │   ├── BossLevelManager
│       │   │    │   ├── CollisionManager
│       │   │    │   ├── GameMechanics
│       │   │    │   ├── InputManager
│       │   │    │   └── LevelManager
│       │   │    ├── Music/                          # Audio management
│       │   │    │   └── AudioManager
│       │   │    └── UI/                             # HUD and interface screens
│       │   │        ├── Controllers
│       │   │        │   ├── AboutWindowController.class
│       │   │        │   ├── InstructionController.class
│       │   │        │   ├── MainMenuController.class
│       │   │        │   └── PauseController.class
│       │   │        └── Interfaces
│       │   │            ├── HUDInterface.class
│       │   │            ├── LoadingInterface.class
│       │   │            ├── MenuInterface.class
│       │   │            └── PauseInterface.class
│       │   ├── Scenes #FXML
│       │   │   ├── AboutWindow.fxml
│       │   │   ├── InstructionsWindow.fxml
│       │   │   ├── MainMenu.fxml
│       │   │   └── PauseMenu.fxml
│       │   ├── StyleSheets #CSS
│       │   │   ├── PauseMenu.css
│       │   │   ├── styles.css
│       │   │   └── Window.css
│       └── Resources               # Project Resources
│           └── Assets/             
│               ├── music           # Game Music
│               ├── sounds          # Game Sound Effects
│               └── textures        # Game Textures
├── pom.xml                                  # Maven configuration
└── README.md 
```
