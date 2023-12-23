from qiskit import QuantumCircuit

qc = QuantumCircuit(8, 8)


qc.h(0)

qc.x(1)

qc.h(1)

qc.h(2)

qc.cx(1, 2)

qc.h(3)

qc.x(4)

qc.h(4)

qc.x(5)

qc.h(5)

qc.h(6)

qc.x(7)

qc.h(7)

qc.ccx(4, 5, 6)

qc.ccx(2, 6, 7)

qc.ccx(0, 4, 5)

qc.measure_all()

print(qc.draw())
