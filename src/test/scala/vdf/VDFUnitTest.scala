// See README.md for license details.

package vdf

import java.io.File

import chisel3.iotesters
import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

class VDFUnitTester(c: VDF, Mod: BigInt) extends PeekPokeTester(c) {
  /**
    * compute the gcd and the number of steps it should take to do it
    *
    * @param a positive integer
    * @param b positive integer
    * @return the GCD of a and b
    */
  def computeVDF(in: BigInt, T: Int, N: BigInt): (BigInt) = {
    var x = in;

    for(i <- 0 until T){
      x = (x * x) % N;
    }

    x
  }

  for (seed_in <- 1 until 1000) {
    poke(c.io.sq_in, seed_in)
    poke(c.io.start, true)
    step(1)
    poke(c.io.start, false)
    step(999)
    expect(c.io.sq_out, computeVDF(seed_in, 1000, Mod))
  }
}


class VDFTester extends ChiselFlatSpec {
  val modulus_1024 = BigInt("124066695684124741398798927404814432744698427125735684128131855064976895337309138910015071214657674309443149407457493434579063840841220334555160125016331040933690674569571217337630239191517205721310197608387239846364360850220896772964978569683229449266819903414117058030106528073928633017118689826625594484331")
  val modulus_128 = BigInt("302934307671667531413257853548643485645")

  assert(chisel3.iotesters.Driver(() => new VDF(4389, 16) ) { c => new VDFUnitTester(c, 4389) })
  assert(chisel3.iotesters.Driver(() => new VDF(modulus_128, 128) ) { c => new VDFUnitTester(c, modulus_128) })
  assert(chisel3.iotesters.Driver(() => new VDF(modulus_1024, 1024) ) { c => new VDFUnitTester(c, modulus_1024) })
  println("SUCCESS!!")
}
