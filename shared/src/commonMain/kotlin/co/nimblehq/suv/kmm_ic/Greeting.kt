package co.nimblehq.suv.kmm_ic

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}