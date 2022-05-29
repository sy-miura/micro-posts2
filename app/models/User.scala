package models

import scalikejdbc._
import skinny.orm._

import java.time.ZonedDateTime
import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Future

case class User(id: Option[Long] = None,
                name: String,
                email: String,
                password: String,
                createAt: ZonedDateTime = ZonedDateTime.now(),
                updateAt: ZonedDateTime = ZonedDateTime.now()
               )

object User extends SkinnyCRUDMapper[User] {
  override def tableName = "users"

  override val columns: Seq[String] = Seq("id", "userid", "name", "email", "password", "create_at", "update_at")

  override def defaultAlias: Alias[User] = createAlias("u")

  val users: mutable.HashMap[UUID, User] = mutable.HashMap()

  private def toNamedValues(record: User): Seq[(Symbol, Any)] = Seq(
    Symbol("name")     -> record.name,
    Symbol("email")    -> record.email,
    Symbol("password") -> record.password,
    Symbol("createAt") -> record.createAt,
    Symbol("updateAt") -> record.updateAt
  )

  override def extract(rs: WrappedResultSet, n: scalikejdbc.ResultName[User]): User =
    autoConstruct(rs, n)

  def create(user: User)(implicit session: DBSession): Long =
    createWithAttributes(toNamedValues(user): _*)

  def update(user: User)(implicit session: DBSession): Int =
    updateById(user.id.get).withAttributes(toNamedValues(user): _*)

  def find(userID: UUID): Future[Option[User]] = Future.successful(
    users.get(userID)
  )

}
