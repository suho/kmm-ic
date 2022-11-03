package co.nimblehq.ic.kmm.suv

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
