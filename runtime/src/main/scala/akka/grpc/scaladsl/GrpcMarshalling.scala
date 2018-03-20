package akka.grpc.scaladsl

import akka.NotUsed
import akka.grpc._
import akka.http.scaladsl.model.{ HttpRequest, HttpResponse }
import akka.stream.Materializer
import akka.stream.scaladsl.{ Sink, Source }
import akka.util.ByteString

import scala.concurrent.Future

object GrpcMarshalling {
  def unmarshal[T](req: HttpRequest)(implicit u: ProtobufSerializer[T], mat: Materializer): Future[T] = {
    val messageEncoding = req.headers.find(_.is("grpc-encoding")).map(_.value())
    req.entity.dataBytes
      .via(Grpc.grpcFramingDecoder(uncompressor(messageEncoding)))
      .map(u.deserialize)
      .runWith(Sink.head)(mat)
  }

  def unmarshalStream[T](req: HttpRequest)(implicit u: ProtobufSerializer[T], mat: Materializer): Future[Source[T, NotUsed]] = {
    // TODO decode compressed streams (similar to above but needs a test)
    Future.successful(
      req.entity.dataBytes
        .mapMaterializedValue(_ ⇒ NotUsed)
        .via(Grpc.grpcFramingDecoder)
        .map(u.deserialize))
  }

  def marshal[T](e: T = Identity)(implicit m: ProtobufSerializer[T], mat: Materializer, codec: Codec): HttpResponse =
    marshalStream(Source.single(e))

  def marshalStream[T](e: Source[T, NotUsed])(implicit m: ProtobufSerializer[T], mat: Materializer, codec: Codec): HttpResponse =
    GrpcResponse(e)

  private def uncompressor(encoding: Option[String]): Option[ByteString ⇒ ByteString] = encoding match {
    case None ⇒ None
    case Some("identity") ⇒ None
    case Some("gzip") ⇒ Some(Gzip.uncompress)
  }
}