# Ethical Hacking Training Report

**TLDR;** This markdown document provides an overview of the results obtained from ethical hacking training, employing various tools distributed across multiple sections.

## Target

The focus of our assessment was the Constantine University website. [Visit the website](https://www.umc.edu.dz).

## PART ONE: Wappalyzer and WhatWeb

Both **Wappalyzer** and **WhatWeb** execute multiple HTTP requests against the target. They scrutinize response headers, HTML, and JavaScript to identify potential vulnerabilities.

Given that **WhatWeb** is a command-line interface (CLI) tool, it can be utilized in conjunction with ProxyChains and Tor relays to enhance anonymity.

### Technologies Scanning

Post-analysis using **Wappalyzer** and **WhatWeb**, we identified the following technologies employed by the target server:

- **Operating System (OS):** Ubuntu (version undetected).
- **Web Server:** Php on Apache 2.4.29 server.
- **JavaScript Framework:** jQuery 1.12.4.
- **Content Management System (CMS):**  Joomla with a version possibly under 3.6.4.

### Vulnerabilities

#### Apache 2.4.29 Vulnerabilities

The Apache 2.4.29 version exhibits several vulnerabilities, with a focus on two having the highest **CVSS** score.

##### CVE-2023-45802:

An HTTP/2 stream can be prematurely reset by the client before the timeout concludes. This susceptibility could lead to attacks such as **slow loris**, overwhelming the server memory with TCP connections.

##### CVE-2022-36760:

An HTTP smuggling attack occurs when an attacker sends an HTTP request containing both the **Transfer-Encoding header and the Content-Length header.** If the backend and frontend servers (proxy/load balancer) are not configured uniformly, the following scenario may unfold:

```http
POST / HTTP/1.1 
Host: vulnerable-website.com 
Content-Length: 13 
Transfer-Encoding: chunked 
22
{message:hello there}
0
GET /admin HTTP/1.1
Host: vulnerable-website.com 
Content-Length: 13 
```

In cases where the frontend server utilizes content length, it interprets the entire packet as a single request. However, the backend server may leverage the Transfer-Encoding header, treating it as two separate requests.

#### jQuery 1.12.4 Vulnerabilities

jQuery, specifically version 1.12.4, has historical vulnerabilities related to DOM manipulation methods like **html** and **append.** These weaknesses may expose the system to cross-site scripting attacks, enabling malicious code injection to extract critical data such as session tokens.

## PART TWO: Burp Suite and Mozilla Observatory

**Mozilla Observatory** comprises a suite of tools designed for scanning web applications to identify vulnerabilities and performance issues. It thoroughly analyzes responses and promptly reports any identified problems.

**Burp Suite** is an advanced web application testing tool that enables you to proxy your web activity through it. This feature allows you to capture HTTP requests, modify them, and repeat the process for comprehensive testing and analysis.

### Vulnerabilities

Following the evaluation with the **Mozilla Observatory**, the site received an F rating due to three significant red flags. This was further confirmed using **Burp Suite**.

#### Lack of Subresource Integrity

A critical issue identified is the absence of Subresource Integrity in the loading of JavaScript assets, such as **jQuery**. The relevant script tag lacks a hash value, which poses a serious security risk. This omission could potentially lead to the injection of malicious code, resulting in Cross-Site Scripting (XSS) vulnerabilities.

```html
<script src="/media/jui/js/jquery.min.js?f247ee525bc8db9bfc9b763cec7136e7" type="text/javascript"></script>
```

#### Lack of Content Security Policy

This is a header that controls the sources that the browser can accept data from. Without it, there is a high chance of getting stored XSS where the server would store malicious script in the database and return it afterward in the HTML response. I confirmed that neither the response contains the Content Security Policy header nor the HTML has a meta tag.

#### Absence of Same Site

The session cookie does not include the same-site setting, which may lead to CSRF attacks. However, after further investigation, it turns out they're using another token for CSRF protection. But regardless, it is still an issue.

```html
<script type="application/json" class="joomla-script-options new">
{
"csrf.token":"86b03380672ffb00deb5110cf40ca4ee","system.paths":{"root":"","base":""
}}
</script>
```
