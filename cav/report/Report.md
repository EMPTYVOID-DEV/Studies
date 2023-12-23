# Report on VerifPal

## Introduction

**Verifpal** is a tool for verifying the security of security protocols. It utilizes a symbolic formal language with easy syntax. You can download VerifPal for any operating system; check the documentation site for more information at [verifpal.com](https://verifpal.com/).

## Formal Language

**Verifpal** is the language for interacting with the VerifPal tool. It begins by specifying if the model has a passive or active attacker. **Principals**, like Alice and Bob, are then defined, and their actions and message exchanges are modeled using VerifPal. Finally, **queries** are formulated to test security, such as whether a passive attacker can read Alice's message.

### Declaring the Attacker

We start our model by defining an attacker, which can be **active** or **passive** using the simple syntax `attacker [passive]` or `attacker [active]`.

### Declaring the Principals

Principals are the subjects of your protocol, such as (Alice, Bob) or (client, server). Defining the principals is done like this:

```vp
principal alice [
   knows private a
]
principal bob [
  knows private b
]
```

### Types in VerifPal

**Verifpal** has three types: constants, equations, and primitives.

#### Constants

Both 'a' and 'b' from the previous example are constants. Constants are protocol information, and the rules governing them include:

- Constants are **immutable**; once assigned, they cannot be reassigned.
- **Global name-space rule**: If a constant is declared by one principal, another principal cannot declare a constant with the same name, even if privately declared.
- **No-referencing**: Constants cannot be assigned to other constants; they can only be assigned to primitives or equations.

##### Declaration of Constants

- **knows:** A constant that is static in every protocol execution. The **public** and **private** quantifiers specify whether this constant is global (even to the attacker) or local to that principle. The **password** quantifier declares a weak and guessable constant.
- **generates:** Allows a principal to describe a "fresh" value, regenerated in each protocol execution (e.g., ephemeral private keys, nonces).
- **leaks:** Specifies that a principal will leak an existing constant to the attacker at the point of leakage.
- **Assignment:** Constants may be declared by assigning them to the result of a primitive or equation expression.

#### Primitives

Primitives are essentially cryptographic methods, and they are **perfect**, meaning there are no flaws in that primitive. We can't say, for example, that the **ENC** function is not secure because the key length is 50 bits.

**Core Primitives:**

1. **ASSERT(a, b):**
   - Checks equality of two values, useful for verifying MAC equality.
   - Output value is not used.

2. **CONCAT(a, b,..):**
   - Concatenates two or more values into one.

3. **SPLIT(CONCAT(a,b))** :
   - Splits a concatenation into its original components.

**Hashing Primitives:**

1. **HASH(a):**
   - Secure hash function similar to SHA-256.
   - Takes 1 to 5 inputs and returns one output.

2. **MAC(key, message):**
   - Keyed hash function for message authentication.

3. **HKDF(salt, secret, info):**
   - Hash-based key derivation function.
   - Produces between 1 and 5 outputs.

4. **PW_HASH(a):**
   - Password hashing function similar to Scrypt or Bcrypt.
   - Produces output suitable for private, secret, or sensitive key material.

**Encryption Primitives:**

1. **ENC(key, plaintext):**
   - Symmetric encryption similar to AES-CBC or ChaCha20.

2. **DEC(key, ENC(key, plaintext)):**
   - Symmetric decryption.

3. **AEAD_ENC(key, plaintext, ad):**
   - Authenticated encryption with associated data.

4. **AEAD_DEC(key, cipher, ad):**
   - Authenticated decryption with associated data.

5. **PKE_ENC(G^key, plaintext):**
   - Public-key encryption.

6. **PKE_DEC(key, cipher):**
   - Public-key decryption.

**Signature Primitives:**

1. **SIGN(key, message):**
   - Classic signature primitive.

2. **SIGNVERIF(G^key, message, signature):**
   - Verifies if a signature can be authenticated.

3. **RINGSIGN(key_a, G^key_b, G^key_c, message):**
   - Ring signature for anonymous signing.

4. **RINGSIGNVERIF(G^a, G^b, G^c, m, ringSignature):**
   - Verifies if a ring signature can be authenticated with one of the public keys of the ring.

**Secret Sharing Primitives:**

1. **SHAMIR_SPLIT(k):**
   - Splits a secret (for example a key) into multiple shares.

2. **SHAMIR_JOIN(sa, sb):**
   - Reconstitutes the secret from two distinct shares.

**Checked Primitives:**

- Certain primitives like ASSERT, SPLIT, AEAD_DEC, SIGNVERIF, and RINGSIGNVERIF are "checkable."
- Adding a question mark (?) after them will make the model execution abort on failure in the case of a passive attacker. In the case of an active attacker, the data collected before the failure can be used in future sessions.

#### Equations

Equations are used to create public components out of the private ones (e.g., RSA, Diffie-Hellman, elliptic curve), or we can use them to construct shared privates. We

 use the exponent `^` to denote an equation; here is an example:

```fp
alice [
   knows private a
   knows private b
   ga = G^a
   gab = ga^b
]
```

##### Note

All equations must have the generator **G** as their root.

### Messages

Messages represent the exchange of constants and can be presented as simply as:

```fp
server -> client : gs,[e]
```

The square brackets mean that the attacker can't tamper with 'e.' You can think of it as the client pre-authenticated the server using certificates, for example.

### Queries

Every VerifPal model finishes with a queries block, defining the things we want to test in the model. Here are all queries in VerifPal:

#### Confidentiality Queries

To put it simply, we check if the message was compromised to the attacker.

```vp 
queries [
  confidentiality? m
]
```

#### Authentication Queries

Here we are checking whether the attacker can impersonate Alice and send the message 'm' to Bob.

```vp
queries [
  Authentication? alice -> bob : m
]
```

#### Freshness Queries

These queries are useful to detect reply attacks in which the attacker can use a static constant across sessions.

```vp
alice [
  generates nonce
]

alice -> server : nonce

queries [
   freshness? nonce
]
```

#### Equivalence Queries

We use equivalence queries to test whether two constants 'a' and 'b' are equal in all protocol sessions. Useful to check that two parties will reach the same key in all scenarios.

#### Unlinkability Queries

These are a bit tricky, but to simplify the meaning, we are checking whether the attacker can recognize that constants 'a' and 'b' were generated using the same primitive or have a common source.

```vp
alice [
   generates a
]
alice -> server : a
server [
  generates s
  leaks l
  h1, h2 = HKDF(a, s, nil)
  h3, h4 = HKDF(l, l, nil)    
]

queries [
   unlinkability? h1, h2
   unlinkability? h3, h4
]
```

## Passive vs Active Attacker

**Verifpal** aims to maximize information extraction as an attacker on the network. In a passive attack, Verifpal deconstructs shared values among principals, potentially reconstructing them. In an active attack, Verifpal can modify unguarded values during network transmission, leading to an unbounded number of modifications across protocol executions. Freshly generated values are session-specific.

An active attacker has more capabilities, including modifying values within messages, crafting and injecting malicious values (e.g., replacing public keys), and executing an unbounded number of sessions. Values learned during a session are retained, except those composed of at least one generated value. Guarded constants and checked primitives become crucial for security, ensuring, for instance, the integrity of long-term public keys and the success of signature verification or authenticated decryption operations.

## Verifpal Analysis

Instead of trying to prove the queries (`aka security goals`), **Verifpal** will run the protocol from the attacker's perspective and try to contradict the selected queries by the user.

The attacker analysis process has these exact five steps:

1. **Gather information:** In this phase, the attacker will collect public constants from principals.
2. **State update:** The attacker adds the newly learned values to his state.
3. **Apply transformation:** The attacker deducts new values using his current state. (passive attacker stops here)
4. **Calculate mutations:** Using his newly learned data, he creates a table of all possible substitutions of the protocol constants, equation exponents, and primitive arguments.
5. **Apply mutations:** The attacker applies the mutations with MIMA (man in the middle attack) then returns to step repeating the process until breaking all queries or there is no more values to learn.

### Transformation Rules

The attacker uses four transformations in order to find new values which are:

- `Resolve`: Assign a constant to primitive or an equation like `ga=ga^b`.
- `Deconstruct`: The attempt to reverse the process of the equation or primitive given knowledge of its arguments or its exponents in the case of an equation.
- `Reconstruct`: The attacker recreates the process of an equation or process given he knows all its inputs.
- `Equivalize`: The attacker attempts to create equal values or prove equality to his current state values.

## Verifpal Optimization

**Verifpal** addresses the challenge faced by symbolic model protocol verifiers when analyzing complex protocols with a large state and value space, often causing termination issues. To optimize analysis, Verifpal employs heuristic techniques, including segmented stages and restrictions. These stages gradually allow more modifications to principals' states, delaying potentially infinite mutation recursion depth until necessary. Key characteristics include:

1. **Stage 1:** Passive attacker analysis, constants, and equation exponents may be mutated to `nil` only and not to each other. (e.g., `g^a` mutates to `g^nil` but not to `g^b` for equations).
2. **Stage 2:** Includes Stage 1 elements and allows mutation of non-explosive primitives within a predetermined call depth. For example, nested hash functions have a limited depth in mutation.
3. **Stage 3:** Adds explosive primitives to Stage 2 mutations.
4. **Stage 4:** Includes Stage 3 elements and permits constants and equation exponents to be replaced with each other, not just `nil`.
5. **Stage 4 and beyond:** Extends Stage 3, allowing primitives a mutation depth of `n-3` where `n` is the current stage, ensuring mutations maintain the same `skeleton` as defined in Stage 2.

#### Notes

1. **Verifpal** is written in Go; that's why it uses `nil`, which means null in other languages, and in the context of symbolic analysis, we mean a random constant chosen by the attacker.
2. Explosive primitives are those related to queried constants.

## Validity of VerifPal

**Verifpal**'s validity is supported by its successful application to model various protocols, including TLS, Signal, Scuttlebutt, Telegram, and ProtonMail. While the evidence aligns with previous analyses of these protocols, Verifpal aims for formal verification confidence. The formal analysis methodology contributes to this assurance by establishing that:

1. If an attacker cannot obtain a value (m), Verifpal deems the query successful for the described protocol.
2. If an attacker cannot find multiple communication paths for a value (e) between principals A and B, where B uses e in a rewrite-capable primitive or equation, then e is authenticated under A → B for the described protocol.

## Examples

Here, we're going to show a couple of models that verify certain protocols with Verifpal.

### Diffie-Hellman Key Exchange

Our goal here is to check the equivalence of keys gab and gba.

```vp
attacker [active]

principal alice[]
principal bob[]

principal alice[
knows private a
ga=G^a
]

alice -> bob: [ga]

principal bob[
knows private b
gb=G^b
gba=ga^b
]

bob -> alice : [gb]  

principal alice [
  gab=gb^a
]

queries[
equivalence? gab , gba
]
```

so conclude that the diffie-hellman protocol is only secure if the attacker can't tamper with public parts ga and gb we can accomplish that using certificates.


### challenge and response protocol 

```vp 
attacker [active]

principal server []

principal alice []

principal alice [
generates nonce
]

alice -> server :nonce

principal server [
knows private s
gs=G^s
proof=SIGN(s,nonce)
]

server -> alice : [gs] , proof

principal alice[
_=SIGNVERIF(gs,nonce,proof)?
generates key
e=PKE_ENC(gs,key)
]

alice -> server : e

principal server[
dkey=PKE_DEC(s,e)
]

queries[
authentication? server->alice:proof
confidentiality? key
]
```

Again the security of the protocol lies on that fact that the attacker can't manipulate gs otherwise the attacker will impersonate the server sending his public key instead with the signature of the nonce.

