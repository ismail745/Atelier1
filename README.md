# MVC2_JEE – Gestion des Commandes 🧾🛍️

Application web Jakarta EE (Servlets + JSP + JSTL + CDI) illustrant le patron MVC2 pour gérer des clients, produits, commandes et lignes de commande.

## ✨ Fonctionnalités
- 👥 Clients: création, liste, édition, suppression
- 📦 Produits: création, liste, édition, suppression
- 🧾 Commandes: création, liste, édition, suppression
- 🧮 Lignes de commande: ajout/suppression, blocage des doublons par commande
- 🌐 UTF‑8 partout (filtre + JSP)
- 🗄️ Init BD auto: création BD, schéma, données démo si tables vides
- 🧭 Navbar fixée (fragments `head.jspf`, `nav.jspf`, `footer.jspf`)

## 🏗️ Stack & Outils
- Jakarta EE 10 (Servlets, JSP, JSTL, CDI)
- MySQL (Connector/J)
- Maven (WAR)
- Déploiement: WildFly/Payara/Tomcat 10+

## 📁 Architecture (MVC2)
- Modèle: `ma.fstt.entities.*`, `ma.fstt.dao.*`, interfaces `ma.fstt.service.*`
- Contrôleurs: `ma.fstt.servlets.*`
- Vues: `src/main/webapp/**` (JSP + JSPF)

```
src/main/java/
  ma/fstt/entities/    # Client, Produit, Commande, LigneDeCommande
  ma/fstt/dao/         # DAO + JdbcUtils
  ma/fstt/servlets/    # ClientServlet, ProduitServlet, CommandeServlet, ...
  ma/fstt/startup/     # DatabaseInitializer (schema+data)
  ma/fstt/filters/     # CharacterEncodingFilter (UTF-8)
src/main/webapp/
  clients/, produits/, commandes/
  WEB-INF/jspf/        # head.jspf, nav.jspf, footer.jspf
  css/site.css
```

## ⚙️ Configuration
Fichier `src/main/resources/db.properties`:
```
jdbc.url=jdbc:mysql://localhost:3306/mvc2jee?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC
jdbc.user=root
jdbc.password=
```
Au démarrage:
- Crée la BD si absente (utf8mb4)
- Exécute `db/schema.sql`
- Exécute `db/data.sql` seulement si tables vides

## 🚀 Lancer
```
mvn clean package
```
Déployez `target/MVC2_JEE-1.0-SNAPSHOT.war` sur votre serveur Jakarta EE 10+. Accès:
```
http://localhost:8080/MVC2_JEE-1.0-SNAPSHOT/
```
(l’accueil redirige vers la liste des commandes)

## 🧭 Parcours UI
- Navbar: Clients | Produits | Commandes
- Commandes: détail + lignes; impossible d’ajouter 2× le même produit
- Création commande: sélection du client via liste déroulante

## ✅ Conformité
- MVC2, Servlets/JSP/JSTL, CDI (@Inject/@ApplicationScoped) OK
- Schéma MySQL et données de démo automatiques
- MySQL Connector et Maven OK

## Notes
- Si accents incorrects: vérifier encodage BD/tables en `utf8mb4`.
- Adapter `db.properties` pour l’environnement.

## Endpoints
- `GET/POST /clients`, `GET /clients/delete?id=...`
- `GET/POST /produits`, `GET /produits/delete?id=...`
- `GET/POST /commandes`, `GET /commandes?form`, `GET /commandes?view={id}`

---
Projet académique/démo.
