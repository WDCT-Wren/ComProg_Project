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

### Project Structure
```
src/
└── main/
    └── java/
        └── org.group1.GamePackage/
            ├── Components/         # Entity components (behavior, animation, timers)
            │   ├── AnimationComponent
            │   ├── BulletAnimationComponent
            │   ├── CupHeadComponent
            │   ├── EnemyAnimationComponent
            │   ├── EnemyDropsAnimationComponent
            │   └── TimerComponent
            ├── Factory/            # Entity and scene factories
            │   ├── BackgroundFactory
            │   ├── EntityFactory
            │   └── MainSceneFactory
            ├── Handlers/           # Game logic and input management
            │   ├── CollisionManager
            │   ├── GameMechanics
            │   ├── InputManager
            │   └── LevelManager
            ├── Music/              # Audio management
            │   └── AudioManager
            ├── UI/                 # HUD and interface screens
            │   ├── HUDInterface
            │   ├── LoadingInterface
            │   └── MenuInterface
            └── Application         # Main entry point
```
