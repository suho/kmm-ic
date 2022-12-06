package co.nimblehq.ic.kmm.suv.data.remote.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = FullSizeUrlSerializer::class)
class Url(val string: String)

object FullSizeUrlSerializer : KSerializer<Url> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Url", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Url) {
        encoder.encodeString(value.string)
    }

    override fun deserialize(decoder: Decoder): Url {
        val string = decoder.decodeString() + "l"
        return Url(string)
    }
}
