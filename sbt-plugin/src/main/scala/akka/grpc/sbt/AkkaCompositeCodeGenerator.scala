/*
 * Copyright (C) 2018 Lightbend Inc. <https://www.lightbend.com>
 */

package akka.grpc.sbt

import akka.grpc.gen.CompositeCodeGenerator

/** An easy to use accessor to be used reflectively in the main sbt project */
class AkkaCompositeCodeGenerator {
  def instance(): protocbridge.ProtocCodeGenerator =
    new ProtocBridgeSbtPluginCodeGenerator(CompositeCodeGenerator)
}
