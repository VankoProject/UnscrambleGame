package com.kliachenko.unscramblegame.game

import android.content.Context

interface GameRepository : Score {
    fun currentWordPosition(): Int
    fun maxWordsCount(): Int
    fun shuffleWord(): String
    fun isTextCorrect(text: String): Boolean
    fun isLastWord(): Boolean
    fun next()
    fun restart()

    class Base(
        private val permanentStorage: PermanentStorage,
        private val shuffle: Shuffle = Shuffle.Reversed(),
        private val wordsCount: Int = 2,
        private val scoreLogic: ScoreLogic = ScoreLogic.Base(permanentStorage),
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
    ) : GameRepository {

        private var shuffled = ""
        override fun currentWordPosition() = permanentStorage.uiPosition()
        override fun maxWordsCount() = wordsCount
        override fun score() = scoreLogic.score()
        override fun shuffleWord(): String {
            if (shuffled.isEmpty()) {
                shuffled = shuffle.shuffle(allWords[permanentStorage.index()])
            }
            return shuffled
        }

        override fun isTextCorrect(text: String): Boolean {
            val isCorrect = allWords[permanentStorage.index()] == text
            scoreLogic.calculate(isCorrect)
            return isCorrect
        }

        override fun isLastWord() = permanentStorage.uiPosition() == wordsCount
        override fun next() {
            shuffled = ""
            permanentStorage.saveIndex(permanentStorage.index() + 1)
            permanentStorage.saveUiPosition(permanentStorage.uiPosition() + 1)
        }

        override fun restart() {
            next()
            permanentStorage.saveUiPosition(1)
            scoreLogic.clear()
            if (permanentStorage.index() == allWords.size) permanentStorage.saveIndex(index = 0)
        }
    }
}

interface PermanentStorage {
    fun index(): Int
    fun saveIndex(index: Int)
    fun uiPosition(): Int
    fun saveUiPosition(uiPosition: Int)
    fun score(): Int
    fun saveScore(score: Int)
    fun attempts(): Int
    fun saveAttempts(attempts: Int)

    class Base(context: Context) : PermanentStorage {

        private val sharedPreferences =
            context.getSharedPreferences("unScrambleStorage", Context.MODE_PRIVATE)

        override fun index() = sharedPreferences.getInt(KEY_INDEX, 0)
        override fun saveIndex(index: Int) {
            sharedPreferences.edit().putInt(KEY_INDEX, index).apply()
        }
        override fun uiPosition() = sharedPreferences.getInt(KEY_UI_POSITION, 1)
        override fun saveUiPosition(uiPosition: Int) {
            sharedPreferences.edit().putInt(KEY_UI_POSITION, uiPosition).apply()
        }
        override fun score(): Int {
            return sharedPreferences.getInt(KEY_SCORE, 0)
        }
        override fun saveScore(score: Int) {
            sharedPreferences.edit().putInt(KEY_SCORE, score).apply()
        }
        override fun attempts(): Int {
            return sharedPreferences.getInt(KEY_ATTEMPTS, 0)
        }
        override fun saveAttempts(attempts: Int) {
            sharedPreferences.edit().putInt(KEY_ATTEMPTS, attempts).apply()
        }

        companion object {
            private const val KEY_INDEX = "index"
            private const val KEY_UI_POSITION = "uiPosition"
            private const val KEY_SCORE = "key_score"
            private const val KEY_ATTEMPTS = "key_attempts"
        }
    }
}

interface Score {
    fun score(): Int
}

interface ScoreLogic : Score {

    fun calculate(isCorrect: Boolean)
    fun clear()

    abstract class Abstract(
        protected val permanentStorage: PermanentStorage
    ) : ScoreLogic {
        override fun score(): Int = permanentStorage.score()
        override fun clear() {
            permanentStorage.saveScore(score = 0)
            permanentStorage.saveAttempts(attempts = 0)
        }
    }

    class Base(permanentStorage: PermanentStorage) : Abstract(permanentStorage) {
        override fun calculate(isCorrect: Boolean) {
            var score = permanentStorage.score()
            var attempts = permanentStorage.attempts()
            if (isCorrect) {
                score += 10
                if (attempts == 0)
                    score += 10
                attempts = 0
            } else
                attempts++
            permanentStorage.saveScore(score)
            permanentStorage.saveAttempts(attempts)
        }
    }
}

interface Shuffle {
    fun shuffle(source: String): String
    class Base : Shuffle {
        override fun shuffle(source: String): String {
            val array = source.toCharArray()
            array.shuffle()
            val stringBuilder = StringBuilder()
            array.forEach {
                stringBuilder.append(it)
            }
            return stringBuilder.toString()
        }
    }

    class Reversed : Shuffle {
        override fun shuffle(source: String) = source.reversed()
    }
}