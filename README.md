# AndroidAnimations

This is the project where we will analyze, study, and put into practice how to work with animations in Android.

- We are going to use [Kotlin][kotlin].
- We are going to practice pair programming.

---

## Getting started

The repository contains a lot of screens with different uses of many Android frameworks for animations. If you are running the app from `master` some of them will crash with a `TODO` exception because we are expecting you to implement them. If you want to see the final application running move to branch `06-coordinator-layout`.

There are `TODO` comments all around the code to practice the different frameworks, they are completely independent from each other so just pick the one you want to practice and implement it.

## Tasks

Your task is to learn about Android animations and apply all these techniques to the app. We recommend you to follow exercises in a very specific order but feel free to do it your way.

The recommended order for exercises is:

- Before starting
  1. Fork this repository.
  2. Checkout `master` branch.
- Solve these exercises:
   1. [Path drawing][path_drawing]
   2. [View animation][view_animation]
   3. [Property animation][property_animation]
   4. [Drawable graphics][drawable_graphics]
   5. [Layout transition][layout_transition]
   6. [Shared elements][shared_elements]
   7. [Coordinator layout][coordinator_layout]

## Considerations

If you get stuck, `06-coordinator-layout` branch contains the finished kata using all animation frameworks. There are also branches for every single new library following the above order and PRs for them showcasing what are the steps we followed to finish the whole exercise.

---

## Documentation

Here are some links we hope you can find useful to finish these tasks:

### Basics (Paths, interpolators, etc.)

- [Path reference][path_drawing]
- [Measure... Layout... Draw!][measure_layout_draw]
- [Measure, Layout, Draw, Repeat][measure_layout_draw_repeat]
- [How Android Draws Views][how_android_draws_views]
- [Playing with Paths][playing_with_paths]
- [Android Interpolators: A Visual Guide][android_interpolators_a_visual_guide]

### View animation

- [View Animation][view_animation]

### Property animation

- [Property animation][property_animation]
- [DevBytes: Property Animations][dev_bytes_property_animations]
- [Move a View with Animation][move_a_view_with_animation]

### Drawable graphics

- [Animate drawable graphics][drawable_graphics]
- [AnimationDrawable][animation_drawable]
- [Frame Animations in Android][frame_animations_in_android]
- [AnimationVectorDrawable][animation_vector_drawable]
- [Lottie][lottie]

### Layout transition

- [Layout transition][layout_transition]
- [DevBytes: Layout Transitions][dev_bytes_layout_transition]
- [Animate all the things. Transitions in Android][animate_all_the_things]

### Shared elements

- [Shared Element Activity Transition][shared_element_activity_transition]
- [Postponed Shared Element Transitions][postponed_shared_element_transitions]
- [Android Shared-Element Transitions for all][android_shared_Element_transitions_for_all]
- [A window into transitions: Google I/O 2016][a_window_into_transitions]
- [Material Animations][material_animations]

### Coordinator layout

- [Android Design — Coordinator Layout][android_design_coordinator_layout]
- [Mastering the Coordinator Layout][mastering_the_coordinator_layout]
- [AppBarLayout scroll behavior with layout_scrollFlags][app_bar_layout_scroll_behavior]
- [Handling Scrolls with CoordinatorLayout][handling_scrolls_with_coordinator_layout]
- [Intercepting everything with CoordinatorLayout Behaviors][intercepting_everything]
- [Animation Collapsing Toolbar Android][how_to_animate_collapsing_avatar]

## License

Copyright 2019 Karumi

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[karumilogo]: https://cloud.githubusercontent.com/assets/858090/11626547/e5a1dc66-9ce3-11e5-908d-537e07e82090.png
[kotlin]: https://kotlinlang.org/
[path_drawing]: https://developer.android.com/reference/android/graphics/Path
[view_animation]: https://developer.android.com/guide/topics/graphics/view-animation
[property_animation]: https://developer.android.com/guide/topics/graphics/prop-animation
[drawable_graphics]: https://developer.android.com/guide/topics/graphics/drawable-animation
[layout_transition]: https://developer.android.com/training/transitions
[shared_elements]: https://developer.android.com/training/transitions/start-activity
[coordinator_layout]: https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout
[measure_layout_draw]: https://medium.com/@britt.barak/measure-layout-draw-483c6a4d2fab
[measure_layout_draw_repeat]: https://www.youtube.com/watch?v=4NNmMO8Aykw
[how_android_draws_views]: https://developer.android.com/guide/topics/ui/how-android-draws
[playing_with_paths]: https://medium.com/androiddevelopers/playing-with-paths-3fbc679a6f77
[android_interpolators_a_visual_guide]: https://thoughtbot.com/blog/android-interpolators-a-visual-guide
[dev_bytes_property_animations]: https://www.youtube.com/watch?v=3UbJhmkeSig
[move_a_view_with_animation]: https://developer.android.com/training/animation/reposition-view
[animation_drawable]: https://developer.android.com/reference/android/graphics/drawable/AnimationDrawable
[frame_animations_in_android]: https://www.bignerdranch.com/blog/frame-animations-in-android/
[animation_vector_drawable]: https://developer.android.com/reference/android/graphics/drawable/AnimatedVectorDrawable
[lottie]: https://airbnb.design/lottie/
[dev_bytes_layout_transition]: https://www.youtube.com/watch?v=55wLsaWpQ4g
[animate_all_the_things]: https://medium.com/@andkulikov/animate-all-the-things-transitions-in-android-914af5477d50
[shared_element_activity_transition]: https://guides.codepath.com/android/Shared-Element-Activity-Transition
[postponed_shared_element_transitions]: https://www.androiddesignpatterns.com/2015/03/activity-postponed-shared-element-transitions-part3b.html
[android_shared_Element_transitions_for_all]: https://medium.com/@aitorvs/android-shared-element-transitions-for-all-b90e9361507d
[a_window_into_transitions]: https://www.youtube.com/watch?v=4L4fLrWDvAU
[material_animations]: https://gist.github.com/lopspower/1a0b4e0c50d90fbf2379
[android_design_coordinator_layout]: https://medium.com/martinomburajr/android-design-coordinator-layout-1-an-introduction-10a1b91ded28
[mastering_the_coordinator_layout]: http://saulmm.github.io/mastering-coordinator
[app_bar_layout_scroll_behavior]: https://medium.com/@tonia.tkachuk/appbarlayout-scroll-behavior-with-layout-scrollflags-2eec41b4366b
[handling_scrolls_with_coordinator_layout]: https://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout
[intercepting_everything]: https://medium.com/androiddevelopers/intercepting-everything-with-coordinatorlayout-behaviors-8c6adc140c26
[how_to_animate_collapsing_avatar]: https://medium.com/@anatoliy8827/how-to-animate-collapsing-avatar-toolbar-sample-f3f37ab6c35e