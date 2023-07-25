import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductBill
import com.mukarram.businessmanagementapp.DatabaseApp.DataClasses.ProductEntry
import java.lang.reflect.Type

object ProductBillJsonAdapter : JsonDeserializer<ProductBill>, JsonSerializer<ProductBill> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ProductBill {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON")

        val id = jsonObject.getAsJsonPrimitive("id").asLong
        val billId = jsonObject.getAsJsonPrimitive("billId").asLong
        val totalBill = jsonObject.getAsJsonPrimitive("totalBill").asDouble
        val productEntries = context?.deserialize<List<ProductEntry>>(
            jsonObject.getAsJsonArray("productEntries"),
            object : TypeToken<List<ProductEntry>>() {}.type
        ) ?: emptyList()

        return ProductBill(id, billId, totalBill, productEntries)
    }

    override fun serialize(src: ProductBill?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty("id", src?.id)
        jsonObject.addProperty("billId", src?.billId)
        jsonObject.addProperty("totalBill", src?.totalBill)
        jsonObject.add("productEntries", context?.serialize(src?.productEntries))

        return jsonObject
    }
}
