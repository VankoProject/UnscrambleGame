package com.kliachenko.unscramblegame

interface GameRepository {
    fun currentWordPosition(): Int
    fun maxWordsCount(): Int
    fun score(): Int
    fun shuffleWord(): String
    fun isTextCorrect(text: String): Boolean
    fun isLastWord(): Boolean
    fun next()
    fun restart()

    class Base(private val shuffle: Shuffle = Shuffle.Reversed(), private val wordsCount: Int = 2) :
        GameRepository {
        private val allWords: List<String> = listOf(
                "animal",
                "auto",
                "anecdote",
                "alphabet",
                "all",
                "awesome",
                "arise",
                "balloon",
                "basket",
                "bench",
                "best",
                "birthday",
                "book",
                "briefcase",
                "camera",
                "camping",
                "candle",
                "cat",
                "cauliflower",
                "chat",
                "children",
                "class",
                "classic",
                "classroom",
                "coffee",
                "colorful",
                "cookie",
                "creative",
                "cruise",
                "dance",
                "daytime",
                "dinosaur",
                "doorknob",
                "dine",
                "dream",
                "dusk",
                "eating",
                "elephant",
                "emerald",
                "eerie",
                "electric",
                "finish",
                "flowers",
                "follow",
                "fox",
                "frame",
                "free",
                "frequent",
                "funnel",
                "green",
                "guitar",
                "grocery",
                "glass",
                "great",
                "giggle",
                "haircut",
                "half",
                "homemade",
                "happen",
                "honey",
                "hurry",
                "hundred",
                "ice",
                "igloo",
                "invest",
                "invite",
                "icon",
                "introduce",
                "joke",
                "jovial",
                "journal",
                "jump",
                "join",
                "kangaroo",
                "keyboard",
                "kitchen",
                "koala",
                "kind",
                "kaleidoscope",
                "landscape",
                "late",
                "laugh",
                "learning",
                "lemon",
                "letter",
                "lily",
                "magazine",
                "marine",
                "marshmallow",
                "maze",
                "meditate",
                "melody",
                "minute",
                "monument",
                "moon",
                "motorcycle",
                "mountain",
                "music",
                "north",
                "nose",
                "night",
                "name",
                "never",
                "negotiate",
                "number",
                "opposite",
                "octopus",
                "oak",
                "order",
                "open",
                "polar",
                "pack",
                "painting",
                "person",
                "picnic",
                "pillow",
                "pizza",
                "podcast",
                "presentation",
                "puppy",
                "puzzle",
                "recipe",
                "release",
                "restaurant",
                "revolve",
                "rewind",
                "room",
                "run",
                "secret",
                "seed",
                "ship",
                "shirt",
                "should",
                "small",
                "spaceship",
                "stargazing",
                "skill",
                "street",
                "style",
                "sunrise",
                "taxi",
                "tidy",
                "timer",
                "together",
                "tooth",
                "tourist",
                "travel",
                "truck",
                "under",
                "useful",
                "unicorn",
                "unique",
                "uplift",
                "uniform",
                "vase",
                "violin",
                "visitor",
                "vision",
                "volume",
                "view",
                "walrus",
                "wander",
                "world",
                "winter",
                "well",
                "whirlwind",
                "x-ray",
                "xylophone",
                "yoga",
                "yogurt",
                "yoyo",
                "you",
                "year",
                "yummy",
                "zebra",
                "zigzag",
                "zoology",
                "zone",
                "zeal"
            )
        private var uiPosition = 1
        private var index = 0
        private var score = 0
        private var attempts = 0
        override fun currentWordPosition() = uiPosition
        override fun maxWordsCount() = wordsCount
        override fun score() = score
        override fun shuffleWord() = shuffle.shuffle(allWords[index])
        override fun isTextCorrect(text: String): Boolean {
            val isCorrect = allWords[index] == text
            if (isCorrect) {
                score += if (attempts == 0) 20 else 10
                attempts = 0
            } else attempts++
            return isCorrect
        }
        override fun isLastWord() = uiPosition == wordsCount
        override fun next() {
            index++
            uiPosition++
        }
        override fun restart() {
            uiPosition = 1
            score = 0
            attempts = 0
            index++
            if (index == allWords.size) index = 0

        }
    }
}

interface Shuffle {
    fun shuffle(source: String): String
    class Base() : Shuffle {
        override fun shuffle(source: String): String {
            return source.toCharArray().shuffle().toString()
        }
    }

    class Reversed() : Shuffle {
        override fun shuffle(source: String) = source.reversed()
    }
}