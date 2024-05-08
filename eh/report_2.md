### Same Target: Constantine University Website

For an in-depth analysis of the Constantine University website, visit the [official website](https://www.umc.edu.dz).


### BuiltWith Analysis

This web application simplifies the process of conducting thorough analyses of the technologies utilized by a website. Below are the results of our investigation:

- **Google Analytics:** Employed for website traffic analysis.
- **Cloudflare Radar:** Utilized to detect emerging attack trends.
- **Jsdelivr and OSS CDN:** Content Delivery Network solutions utilized for optimized content delivery.

### Gobuster Analysis

**Gobuster** serves as a powerful alternative to DirBuster, offering superior performance and command-line functionality.

#### DNS Mode Results

In DNS mode, Gobuster efficiently identifies subdomains by employing DNS resolution and certificate checking. The following subdomains were discovered during our assessment:

- Archives.umc.edu.dz [41.111.178.106]
- Depot.umc.edu.dz [41.111.178.106]
- Doc.umc.edu.dz [41.111.178.109]

While the number of identified subdomains is limited, this insight proves valuable for future investigations, particularly noting the presence of an archive subdomain.

#### Dir Mode Results

Gobuster's Dir mode facilitates the discovery of directories by scanning with a provided wordlist. Our examination yielded the following results:

- /. (Status: 200) [Size: 119608]
- /0 (Status: 200) [Size: 119608]
- /116-convergence (Status: 200) [Size: 124149]
- /116-screens (Status: 200) [Size: 124145]
- /116-roadcharging (Status: 200) [Size: 124150]
- /?? (Status: 200) [Size: 119607]
- /Communication (Status: 200) [Size: 82847]
- /HOME (Status: 200) [Size: 69261]
- /Home (Status: 200) [Size: 69261]
- /Site-Map (Status: 200) [Size: 487575]

While no significant anomalies were detected, the extensive scan yielded valuable insights, demonstrating 18% progress after 10 minutes of execution.