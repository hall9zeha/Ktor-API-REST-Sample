package dev.barryzeha.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.BSONDecoder
import org.bson.codecs.kotlinx.BsonDecoder
import org.bson.codecs.kotlinx.BsonEncoder
import org.bson.types.ObjectId

object ObjectIdSerializer: KSerializer<ObjectId>{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ObjectId", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ObjectId) {
        if(encoder is BsonEncoder){
            encoder.encodeObjectId(value)
        }else {
            encoder.encodeString(value.toHexString())
        }
    }

    override fun deserialize(decoder: Decoder): ObjectId {
        if(decoder is BsonDecoder){
            return decoder.decodeObjectId()
        }
       return ObjectId(decoder.decodeString())
    }
}