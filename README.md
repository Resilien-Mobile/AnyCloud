# AnyCloud

An Android notification-first companion for reminders, live task progress, and device power insights.

## Features

* Any Cloud: Turn quick thoughts into persistent notifications and keep important items where they remain visible.
* Task Cloud: Show task progress through Live Update notifications, keeping current tasks, completion status, and remaining items up to date.
* Task Management: Add, complete, and delete tasks, with changes reflected in both the app and notifications.
* Task Ordering: Reorder Task Cloud items with long-press drag gestures, with persistent ordering.
* Power Cloud: View battery percentage, charging adapter type, and real-time charging wattage in one place.
* Live Power Notification: Keep charging wattage visible through a continuously updated notification.
* Annual Activity Track: Record daily activity and visualize it in the navigation drawer.
* Cumulative Statistics: Track total active days and total planned items.
* Local Persistence: Store notification configs, task order, and statistics locally with Room.
* Modern Android Stack: Built with Kotlin, Jetpack Compose, Material Design, Hilt, and Navigation.

## Roadmap

* [x] Complete the basic persistent notification flow for Any Cloud
* [x] Complete Live Update task progress notifications for Task Cloud
* [x] Complete battery and charging status display for Power Cloud
* [x] Add local persistence and cumulative statistics
* [ ] Improve settings and notification display options
* [ ] Refine animations, haptic feedback, and interaction details
* [ ] Add a built-in local LLM and bring pickup codes onto the live island
* [ ] Add clipboard follow-up, understanding where users may want to go
* [ ] Publish to F-Droid and Google Play

## Why I Built AnyCloud

AnyCloud started from my observations of modern mobile app experiences.

When using the ChatGPT app on iOS, I noticed the level of polish in its visual motion, interface transitions, and haptic feedback. The liquid-glass-like melting and expansion effects, together with the subtle vibration after a model response completes, made me wonder: why are so few Android apps willing to pursue this kind of experience?

At the same time, I have always felt that traditional reminder apps are often overdesigned. In many cases, users do not need to add a subtitle, location, deadline, or a full set of metadata for a simple thought. What users really care about is often much simpler:

What do I want to do?  
What have I forgotten?

AnyCloud tries to return to that direct starting point: putting thoughts, task progress, and device status where users can actually see them, while reducing the friction between writing something down and checking it later.

This project is also a response to the current Android ecosystem. Some Chinese OEMs have placed too much control over system-level experiences, while also misguiding users around Live Update-like capabilities. Features that should be open, universal, and user-centered are becoming closed and distorted.

So AnyCloud is an attempt to reorganize what the notification area can do.

It aims to make the notification area a lightweight but reliable personal workspace through a more open, restrained, and polished approach.

In short, what AnyCloud wants to do is simple:

Put important things where they can be seen.

Create more open-source alternatives.

And bring more advanced, better technology to everyone.

---

## My Stance

I have always believed in one sentence:

Truth will eventually pierce through lies, and technology should be equal for everyone.

For those who still believe in openness, still take every idea seriously, and still choose hope.
