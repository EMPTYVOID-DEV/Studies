<html>

<head>
	<title>Devoir</title>
</head>

<body>
	<h1>devoir</h1>
	<?php
	// xss prevention one
	$login = htmlspecialchars($_GET['login']);
	$password = $_GET['password'];
	$hashed_password = password_hash($password, PASSWORD_DEFAULT);

	$conn = new mysqli("bd.site.com", "user187232", "l?kd7js2j8+l5", "applinet");

	if ($conn->connect_error) {
		die("Probleme de connexion " . mysqli_connect_error());
	}

	// sql injection prevention one
	$stmt = $conn->prepare("SELECT * FROM users WHERE login = ? AND password = ?");
	$stmt->bind_param("ss", $login, $hashed_password);
	$stmt->execute();
	$result = $stmt->get_result();
	$stmt->close();

	if ($result->num_rows != 1) {
		echo "Login ou mot de passe invalide ! " . $login . " ";
		exit;
	}

	echo "Bonjour " . $login . ". Vous etes maintenant connecte a votre espace de travail<br>";
	// preventing client js to access the cookie and sending the cookie in http.
	setcookie("login", $login, [
		'expires' => time() * 6 + 3600,
		'secure' => true,
		'httponly' => true,
	]);

	if (!isset($_GET['new_avatar'])) exit;
	$allowed_types = array('jpeg', 'png', 'bmp');
	$file_ext = strtolower(pathinfo($_FILES['uploaded']['name'], PATHINFO_EXTENSION));

	// rejecting files bigger than 4mb
	if ($_FILES['uploaded']['size'] > 4096000) {
		echo "File is too large.";
		exit;
	}

	// rejecting files that re not jpeg , png , bmp.
	if (!in_array($file_ext, $allowed_types)) {
		echo "Invalid file type.";
		exit;
	}

	// xss prevention two
	$blacklist = array('<script>', '</script>', 'onerror', 'onload', 'javascript:');
	$name = $_FILES['uploaded']['name'];

	foreach ($blacklist as $item) {
		$name = str_replace($item, '', $name);
	}

	// sql injection prevention two
	$name = mysqli_real_escape_string($conn, $name);
	$login = mysqli_real_escape_string($conn, $login);
	$path = '/var/shared/avatars/' . basename($name);
	move_uploaded_file($_FILES['uploaded']['tmp_name'], $path);
	$query = "UPDATE users set path = '" . $path . "' where login = '" . $login . "'";
	$result = mysqli_query($conn, $query);

	if ($result == TRUE) {
		echo "Le chemin vers le fichier " . $path . " a bien ete enregistre<br>";
		exit;
	}
	echo "Erreur lors de l'ecriture dans la base de donnees" . mysqli_error($conn) . "<br>";

	$conn->close();

	?>
</body>

</html>


<!-- Answers 
 
a xss url : 	http://www.site.com/devoir.php?login=<script>location='https://www.google.com'</script>&password=13

b  - for login field write 'admin' and for password field write 'x OR 1=1'
   - so the action url will be something like this : http://www.site.com/devoir.php?login=admin&password=x%20or%201=1
	 - %20 is url encoding for space.

c  To retrieve the first letter of the admin password  we can do this :
   - for login field we write 'admin' 
	 - for password field we write this : 'x OR 1=1 AND SUBSTR(password, 1, 1) = 'a''
	 - if we login successfully the password starts with 'a' otherwise we repeat for all possible letters.
	 - this is a blind sql injection since we re infering the result.
7 : For this question i have added password hashing 
    - The reason for hashing password is to if ever the database get hacked the user passwords re random strings especially since people re-use    
		  passwords
		- I also removed password from echo in line 28 you should not just print the password right on clear.
		
-->