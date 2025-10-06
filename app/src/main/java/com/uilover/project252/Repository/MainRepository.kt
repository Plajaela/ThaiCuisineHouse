package com.uilover.project252.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uilover.project252.Domain.CategoryModel
import com.uilover.project252.Domain.ItemModel
import com.uilover.project252.R

class MainRepository {

    // 1) หมวดหมู่ 4 ภาค (เหมือนเดิม แต่โลคอล)
    fun loadCategory(): LiveData<MutableList<CategoryModel>> =
        MutableLiveData(mutableListOf(
            CategoryModel("Northern", 101),
            CategoryModel("Southern", 102),
            CategoryModel("Central", 103),
            CategoryModel("Northeastern", 104),
        ))

    // 2) Popular
    fun loadPopular(): LiveData<MutableList<ItemModel>> =
        MutableLiveData(mutableListOf(
            // TODO: popular
            ItemModel(7, 102, "Gaeng Leung", "Spicy and sour fish curry", "Southern Thai Sour Curry, is a spicy, tangy yellow curry made without coconut milk. It combines turmeric, chili, and tamarind for a bold flavor and is often cooked with fish and vegetables like bamboo shoots or green papaya.", R.drawable.gangyellow),
            ItemModel(13,103,"Tom yum Goong","Spicy and sour shrimp soup","A classic Thai dish famous for its bold flavors. It combines lemongrass, kaffir lime leaves, galangal, chili, and lime juice with fresh shrimp, creating a fragrant, spicy, and tangy broth that is both refreshing and comforting.",R.drawable.tomyumgung),
            ItemModel(15,103,"Green Curry","Spicy green curry with chicken or beef","One of Thailand’s most popular curries. It features a fragrant coconut milk base infused with green curry paste, chili, and aromatic herbs, giving it a rich, spicy, and slightly sweet flavor. Typically cooked with tender chicken or beef, along with eggplant, bamboo shoots, and fresh basil, this curry is enjoyed with steamed rice or rice noodles for a comforting and flavorful meal.",R.drawable.greencurry),
            ItemModel(18,104,"Som Tam","Thai green papaya salad","A vibrant dish that blends shredded unripe papaya with chili, lime, fish sauce, peanuts, and tomatoes. It delivers a perfect balance of spicy, sour, salty, and sweet flavors, making it a refreshing and iconic Thai street food.",R.drawable.somtum)
        ))

    // 3) Special (เช่นเดียวกัน)
    fun loadSpecial(): LiveData<MutableList<ItemModel>> =
        MutableLiveData(mutableListOf(
            // TODO: special
            ItemModel(1,101,"Gaeng Hang Lay","Burmese-influenced pork curry","A traditional Northern Thai curry with Burmese influence, known for its rich, aromatic, and slightly sweet-sour flavor. Unlike other Thai curries, it doesn’t use coconut milk; instead, it’s made with pork belly or pork shoulder simmered in a curry paste of dried chilies, garlic, ginger, turmeric, and tamarind.",R.drawable.hanglay),
            ItemModel(16,103,"Kuai Tiao Ruea","Noodles in rich pork or beef broth","A famous Thai noodle soup originally sold from boats along Bangkok’s canals. It features a rich, dark broth flavored with spices, soy sauce, and sometimes a touch of pig or cow’s blood for extra depth.Served with beef or pork, meatballs, and fresh herbs, it’s typically enjoyed in small bowls, encouraging diners to order multiple servings.",R.drawable.noodleboat),
            ItemModel(17,103,"Massaman Curry","Muslim-influenced mild curry","A rich and aromatic Thai curry with Persian influence, known for its mildly spicy, slightly sweet, and nutty flavor. It is made with a blend of spices such as cinnamon, cardamom, cloves, and nutmeg, simmered with coconut milk for a creamy base.",R.drawable.massmuncurry),
            ItemModel(9, 102, "Khao Yam", "Southern-style rice salad with herbs.", "A traditional Southern Thai rice salad that highlights freshness and balance of flavors. It combines steamed rice with a variety of finely chopped herbs, vegetables, toasted coconut, and sometimes edible flowers, then tossed with a tangy nam budu (fermented fish sauce) dressing.", R.drawable.khaoyam)

        ))

    // 4) รายการอาหารตามภาค (ดึงด้วย categoryId เดิม)
    fun loadCategoryItems(categoryId: String): LiveData<MutableList<ItemModel>> {
        val id = categoryId.toIntOrNull()
        val data = when (id) {
            101 -> northernFoods
            102 -> southernFoods
            103 -> centralFoods
            104 -> northeasternFoods
            else -> mutableListOf()
        }
        return MutableLiveData(data)
    }

    // ---------- ข้อมูลโลคอลจริง (แก้/เพิ่มได้ที่นี่ไฟล์เดียว)

    private val northernFoods = mutableListOf<ItemModel>(
        ItemModel(1,101,"Gaeng Hang Lay","Burmese-influenced pork curry","A traditional Northern Thai curry with Burmese influence, known for its rich, aromatic, and slightly sweet-sour flavor. Unlike other Thai curries, it doesn’t use coconut milk; instead, it’s made with pork belly or pork shoulder simmered in a curry paste of dried chilies, garlic, ginger, turmeric, and tamarind.",R.drawable.hanglay),
        ItemModel(2,101,"Khao Soi","Creamy curry noodle soup with both soft and crispy egg noodles, usually served with chicken or beef.","A signature Northern Thai curry noodle soup made with coconut milk, curry paste, and both soft egg noodles and crispy fried noodles on top. It is usually served with chicken or beef, accompanied by pickled mustard greens, shallots, and lime.",R.drawable.khaosoi),
        ItemModel(3,101,"Nam Prik Noom","A Northern Thai green chili dip made from roasted green chilies, garlic, and shallots.","Roasted green chilies, garlic, and shallots are blended to create this classic Northern Thai dip. Smoky with a gentle heat, it is typically served alongside sticky rice, pork cracklings, and fresh vegetables.",R.drawable.namphrignoom),
        ItemModel(4,101,"Sai Ua","Aromatic, spicy pork sausage packed with herbs, grilled to smoky perfection.","A Northern Thai grilled pork sausage infused with fresh herbs and spices such as lemongrass, kaffir lime leaves, galangal, and chilies.",R.drawable.saiua),
        ItemModel(5,101,"Nam Prik Ong","Savory pork and tomato chili dip, eaten with sticky rice and fresh vegetables.","A Northern Thai chili-based dip made with ground pork, tomatoes, and dried chilies, reflecting the region’s preference for savory yet mild flavors.",R.drawable.namprigong),
        ItemModel(6,101,"Kaeb Moo","Crispy pork cracklings","Crispy pork cracklings deep-fried to golden perfection. Crunchy and salty, eaten as a snack or side. Often paired with chili dips like Nam Phrik Noom.",R.drawable.kabmooh)
    )

    private val southernFoods = mutableListOf<ItemModel>(
        ItemModel(7, 102, "Gaeng Leung", "Spicy and sour fish curry", "Southern Thai Sour Curry, is a spicy, tangy yellow curry made without coconut milk. It combines turmeric, chili, and tamarind for a bold flavor and is often cooked with fish and vegetables like bamboo shoots or green papaya.", R.drawable.gangyellow),
        ItemModel(8, 102, "Gaeng Tai Pla", "Fermented fish innards curry.", "A traditional Southern Thai curry known for its intense, bold flavors and dark, rich color. It is made from a fermented fish innards paste (tai pla), which gives the dish its signature salty and umami taste.", R.drawable.gangtaipla),
        ItemModel(9, 102, "Khao Yam", "Southern-style rice salad with herbs.", "A traditional Southern Thai rice salad that highlights freshness and balance of flavors. It combines steamed rice with a variety of finely chopped herbs, vegetables, toasted coconut, and sometimes edible flowers, then tossed with a tangy nam budu (fermented fish sauce) dressing.", R.drawable.khaoyam),
        ItemModel(10, 102, "Moo Hong", "Phuket-style braised pork belly","A classic Southern Thai braised pork dish with Chinese Hokkien influence. It features pork belly or pork shoulder slowly simmered in a savory-sweet sauce made from garlic, black pepper, soy sauce, palm sugar, and aromatic spices until the meat becomes tender and flavorful.",R.drawable.moohong),
        ItemModel(11,102,"Khanom Jeen Nam Ya Tai","A Southern Thai rice noodle dish topped with spicy fish curry sauce.","This dish features soft rice noodles served with a rich curry made from ground fish, turmeric, and aromatic herbs. The sauce is intensely spicy, bold, and full of Southern flavors, often balanced with fresh vegetables, pickles, and boiled eggs on the side.",R.drawable.kanomjeansouth),
        ItemModel(12,102,"Kua Kling","Dry-fried minced pork or beef with Southern curry paste, spicy and aromatic.","Made by stir-frying minced pork or beef with Southern curry paste until dry and fragrant. The dish is intensely spicy, aromatic, and packed with herbs, often eaten with fresh vegetables to balance the heat.",R.drawable.kuakling)

    )

    private val centralFoods = mutableListOf<ItemModel>(
        ItemModel(13,103,"Tom Yum Goong","Spicy and sour shrimp soup","Thailand’s most famous soup, celebrated for its bold balance of spicy, sour, salty, and aromatic flavors. It is made with fresh shrimp simmered in a fragrant broth infused with lemongrass, kaffir lime leaves, galangal, chilies, and lime juice.",R.drawable.tomyumgung),
        ItemModel(14,103,"Pad Thai","Stir-fried noodles with shrimp or chicken","Thailand’s most iconic stir-fried noodle dish, loved for its balance of sweet, sour, salty, and savory flavors. It features rice noodles stir-fried with eggs, tofu, or shrimp, flavored with tamarind paste, fish sauce, and palm sugar, then topped with bean sprouts, chives, and crushed peanuts.",R.drawable.padthai),
        ItemModel(15,103,"Green Curry","Spicy green curry with chicken or beef","One of Thailand’s most popular curries, known for its creamy texture and vibrant green color from fresh green chilies. The curry is made with coconut milk, green curry paste, and fragrant herbs like kaffir lime leaves, lemongrass, and Thai basil, giving it a spicy yet slightly sweet flavor.",R.drawable.greencurry),
        ItemModel(16,103,"Kuai Tiao Ruea","Noodles in rich pork or beef broth","A famous Thai noodle soup originally sold from boats along Bangkok’s canals. It features a rich, dark broth flavored with spices, soy sauce, and sometimes a touch of pig or cow’s blood for extra depth.",R.drawable.noodleboat),
        ItemModel(17,103,"Massaman Curry","Muslim-influenced mild curry","A rich and aromatic Thai curry with Persian influence, known for its mildly spicy, slightly sweet, and nutty flavor. It is made with a blend of spices such as cinnamon, cardamom, cloves, and nutmeg, simmered with coconut milk for a creamy base.",R.drawable.massmuncurry)
    )

    private val northeasternFoods = mutableListOf<ItemModel>(
        ItemModel(18, 104, "Som Tam", "Thai Papaya Salad", "A vibrant dish that blends shredded unripe papaya with chili, lime, fish sauce, peanuts, and tomatoes. It delivers a perfect balance of spicy, sour, salty, and sweet flavors, making it a refreshing and iconic Thai street food.", R.drawable.somtum),
        ItemModel(19, 104, "Koi", "Raw beef or fish mixed with herbs, lime, and spices", "a traditional dish from Isan (Northeastern Thailand) that resembles a spicy, tangy meat salad. It is typically made with minced raw or lightly blanched meat such as beef, fish, or pork, mixed with roasted rice powder, chili, lime juice, fish sauce, and fresh herbs like mint and cilantro.", R.drawable.goi),
        ItemModel(20,104,"Isan Fermented Sausage","Fermented pork sausage with garlic and sticky rice","A famous fermented sausage from Northeastern Thailand (Isan), loved for its tangy, savory flavor. Made from pork, garlic, and rice, the mixture is stuffed into casings and left to ferment naturally, which gives it a slightly sour taste.",R.drawable.isansausage),
        ItemModel(21,104,"Larb","With pork, chicken, or duck, seasoned with lime, chili, roasted rice powder.","A classic meat salad from Isan (Northeastern Thailand) and Laos, known as the national dish of Laos and a staple in Thai cuisine. It is made with minced meat such as pork, chicken, or beef, seasoned with lime juice, fish sauce, roasted rice powder, and chili, then mixed with fresh herbs like mint and cilantro.",R.drawable.larb),
        ItemModel(22,104,"tomsap","Hot and sour soup with pork bones or beef, packed with herbs.","A spicy and sour soup from Isan (Northeastern Thailand) that is both bold and aromatic. It is usually made with pork ribs or beef, simmered in a clear broth flavored with lemongrass, kaffir lime leaves, galangal, dried chilies, and lime juice.",R.drawable.tomsap)
    )

    fun getAllItems(): List<ItemModel> =
        northernFoods + southernFoods + centralFoods + northeasternFoods

    fun getItemsByIds(ids: Set<Int>): MutableList<ItemModel> {
        if (ids.isEmpty()) return mutableListOf()
        val map = getAllItems().associateBy { it.id }
        return ids.mapNotNull { map[it] }.toMutableList()
    }
}