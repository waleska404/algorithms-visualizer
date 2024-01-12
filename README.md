<h1 align="center">🧮 Algorithms Visualizer</h1></br>
<p align="center">  
An algorithms visualizer Android app using Compose and Hilt based on modern Android tech-stack and MVVM architecture.
</p>
</br>

<p align="center">
  <a href="https://opensource.org/licenses/MIT"><img alt="License" src="https://img.shields.io/badge/License-MIT-yellow.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/waleska404"><img alt="Profile" src="https://img.shields.io/badge/GitHub-waleska404-purple"/></a> 
  <a href="https://github.com/waleska404/algorithms-visualizer"><img alt="Profile" src="https://img.shields.io/github/stars/waleska404/algorithms-visualizer"/></a> 
</p>

<h3 align="center">🚧 IN PROGRESS 🚧</h2>

## Screenshots
<p align="center">
<img src="/gif/home-light.gif" width="270"/>
<img src="/gif/dijkstra-light.gif" width="270"/>
<img src="/gif/bubble-light.gif" width="270"/>
<img src="/gif/home-dark.gif" width="270"/>
<img src="/gif/dijkstra-dark.gif" width="270"/>
<img src="/gif/bubble-dark.gif" width="270"/>
</p>

## Tech stack
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - Clean Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)
- Material Design & Animations.
- JUnit for testing.

## Features
- Sorting Algorithms  
  - Visualize Bubble Sort.
  - Visualize Quick Sort.
  - Change list size.
  - Randomize values of the list.
- Path Finding Algorithms
  - Visualize Dijkstra's algorithm.
  - Tap a cell in the grid to become a wall.
  - Create random walls.
  - Clear the grid. 

## Future Features (Contributions are welcome!)
- Space and time complexity information.
- Speed up the animation.
- Pause the animation.
- Improve animations.
- Other algorithms support: Merge Sort, Dijkstra's algorithm...

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/waleska404/algorithms-visualizer/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/waleska404)__ me for my next creations! 🤓

# License
```xml
MIT License

Copyright (c) 2023 Paula Boyano Ivars

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
