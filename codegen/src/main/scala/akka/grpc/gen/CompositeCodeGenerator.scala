/*
 * Copyright (C) 2018 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.grpc.gen

import akka.grpc.gen.javadsl.JavaBothCodeGenerator
import akka.grpc.gen.scaladsl.ScalaBothCodeGenerator
import com.google.protobuf.compiler.PluginProtos
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse
import protocbridge.Artifact

import scala.collection.JavaConverters._

/**
 * Generate both Java and Scala server-side code, mainly for testing.
 */
object CompositeCodeGenerator extends CodeGenerator {
  override val name = "akka-composite"

  override def run(request: PluginProtos.CodeGeneratorRequest): PluginProtos.CodeGeneratorResponse = {
    val javaResult = JavaBothCodeGenerator.run(request)
    val scalaResult = ScalaBothCodeGenerator.run(request)
    println(javaResult.getFileList.asScala.map(_.getName))
    println(scalaResult.getFileList.asScala.map(_.getName))
    CodeGeneratorResponse.newBuilder()
      .addAllFile(javaResult.getFileList)
      .addAllFile(scalaResult.getFileList)
      .build()
  }

  override def suggestedDependencies: Seq[Artifact] =
    JavaBothCodeGenerator.suggestedDependencies ++ ScalaBothCodeGenerator.suggestedDependencies
}
