import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class Person(
    @Column("person_id") @Id var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String? = null,
    var bio: String? = null
)