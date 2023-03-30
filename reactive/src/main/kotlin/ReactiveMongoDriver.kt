import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoClient
import com.mongodb.rx.client.MongoClients
import com.mongodb.rx.client.Success
import model.Product
import model.User
import rx.Observable

class ReactiveMongoDriver {

    private val client: MongoClient = MongoClients.create(MONGO_URL)

    fun addUser(user: User): Observable<Success> =
        client.getDatabase(DATABASE_NAME)
            .getCollection(USER_COLLECTION)
            .insertOne(user.toDocument())

    fun addProduct(product: Product): Observable<Success> =
        client.getDatabase(DATABASE_NAME)
            .getCollection(PRODUCT_COLLECTION)
            .insertOne(product.toDocument())


    fun getProducts(): Observable<Product> =
        client.getDatabase(DATABASE_NAME)
            .getCollection(PRODUCT_COLLECTION)
            .find()
            .toObservable().map(::Product)

    fun getUser(id: Int): Observable<User> {
        return client.getDatabase(DATABASE_NAME)
            .getCollection(USER_COLLECTION)
            .find(Filters.eq("id", id))
            .toObservable().map(::User)
    }

}