from qiskit import QuantumCircuit, Aer, execute
from qiskit.tools.visualization import plot_histogram


circuit = QuantumCircuit(3, 3)

circuit.barrier()

circuit.h(1)

circuit.cx(1, 2)

circuit.measure([0, 1], [0, 1])

circuit.cx(0, 1)

circuit.measure([0, 1], [0, 1])

circuit.h(0)

circuit.cx(1, 2)

circuit.cz(0, 2)

circuit.z(2).c_if(2)

sm = Aer.get_backend("qasm_simulator")

results = execute(circuit, backend=sm).result()

plot_histogram(results.get_counts(circuit), filename="./result.png")
