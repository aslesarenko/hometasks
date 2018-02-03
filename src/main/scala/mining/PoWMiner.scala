package mining

import scorex.crypto.hash.CryptographicHash32

import scala.math.BigInt
import scala.util.Random

class PoWMiner[HF <: CryptographicHash32](hashFunction: HF) {

  private val MaxTarget: BigInt = BigInt(1, Array.fill(32)((-1).toByte))

  def doWork(data: Array[Byte], difficulty: BigInt): ProvedData = {
    val rnd = new Random()
    var proved: ProvedData = null
    do {
      val nonce = rnd.nextInt()
      proved = ProvedData(data, nonce)
    } while (!validateWork(proved, difficulty))
    proved
  }

  def validateWork(data: ProvedData, difficulty: BigInt): Boolean = realDifficulty(data) >= difficulty

  private def realDifficulty(noncedData: ProvedData): BigInt =
    MaxTarget / BigInt(1, hashFunction.hash(noncedData.bytes))

}
