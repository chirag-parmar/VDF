// See README.md for license details.

package vdf

import chisel3._

/**
  * Compute GCD using subtraction method.
  * Subtracts the smaller from the larger until register y is zero.
  * value in register x is then the GCD
  */
class VDF(Mod: BigInt, Mod_Len: Int) extends Module {
  val io = IO(new Bundle {
    val sq_in = Input(UInt(Mod_Len.W))
    val start = Input(Bool())
    val sq_out = Output(UInt(Mod_Len.W))
  })
  var square = RegInit(UInt(Mod_Len.W), 0.U)

  when(io.start) {
    square := (io.sq_in * io.sq_in) % UInt(Mod)
  } .otherwise {
    square := (square * square) % UInt(Mod)
  }
  io.sq_out := square
}
