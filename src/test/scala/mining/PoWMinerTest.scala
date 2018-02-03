package mining

import org.scalacheck.Gen
import org.scalatest.prop.{PropertyChecks, GeneratorDrivenPropertyChecks}
import org.scalatest.{PropSpec, Matchers}
import scorex.crypto.hash.Blake2b256
import scorex.testkit.generators.CoreGenerators

class PoWMinerTest extends PropSpec
  with PropertyChecks
  with GeneratorDrivenPropertyChecks
  with Matchers
  with CoreGenerators {

  property("should generate valid prof for S") {
    val miner = new PoWMiner(Blake2b256)
    lazy val diffGen: Gen[Int] = Gen.choose(0, 1000000)
    forAll(diffGen, nonEmptyBytesGen) { (difficulty, data) =>
      val proved = miner.doWork(data, difficulty)
      proved.data shouldEqual data
      miner.validateWork(proved, difficulty) shouldBe true
    }
  }
}