from qiskit import QuantumCircuit

qc = QuantumCircuit(4, 4)


qc.x(1)

qc.h(1)

qc.h(2)

qc.h(3)

qc.cx(1, 2)

qc.cx(0, 1)

qc.h(0)

qc.x(1)

qc.cx(2, 3)

qc.h(3)

qc.h(2)

qc.cx(1, 0)

qc.measure(qc[0], 3)

print(qc.draw())
