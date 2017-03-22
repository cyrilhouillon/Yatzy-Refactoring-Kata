# Design Pattern Orgy - Yatzy Refactoring Kata

Ce repository est un fork de https://github.com/emilybache/Yatzy-Refactoring-Kata

Il a été initialisé dans le cadre du Mett-up "[Etude des design patterns](https://www.meetup.com/design-patterns/events/238378964/)" animé par [Yvan](https://github.com/cotonne).

L'objectif était d'implémenter à partir de code existant 6 design patterns.
Mettre en place de manière forcée des solutions à des problèmes qui n'existent pas, ou du moins ne sont pas immédiatement visibles, entraîne de l'over-engeniering, et introduit une couche de "complexité accidentelle" purement technique.
Je pense que, en dehors de l'aspect mise en pratique et implémentation de design pattern, l'un des principaux objectifs de l'exercice était justement de sentir à quel moment un pattern:
- est un peu "forcé" et ajoute de la complexité sans véritable apport
- parvient à résoudre un problème technique du à de précédents choix d'implémentation, mais n'est pas directement lié au "métier"
- calque un concept métier

3 groupes de 6 patterns étaient proposés. Nous avons dans cet exercice choisi les patterns suivants:
- Factory
- Facade
- Strategy
- Builder
- Decorator
- Observer

Le code de départ est issu d'un kata de refactoring, et donc assez loin des standards de qualité. Le temps imparti ne nous a pas permis de nous concentrer sur cet aspect. J'essaierais dans le futur de refactorer le code sans dénaturer les effets de l'ajout des design patterns.

## Le contexte

Le projet se base sur le jeu du [Yatzy](https://fr.wikipedia.org/wiki/Yahtzee) (ou Yam's).
Le code d'origine contient une unique classe Yatzy, représentant le résultat d'un lancer de 5 dés. La classe possède également, pour chaque combinaison possible, une méthode (statique ou non), indiquant pour un lancer donné le score obtenu pour cette combinaison.

## Les patterns implémentés

### Builder

Le premier pattern que nous avons implémenté est le pattern **Builder**.

Au Yatzy, le joueur peut faire 3 lancers succéssifs, et à chaque lancer peut choisir de garder des dés du lancer précédent. Il apparaissait donc opportun de pouvoir instancier le résultat d'un lancer en plusieurs étapes, tout en s'assurant de ne pas pouvoir instancier un résultat incohérent.

L'une des principales questions qui s'est posée à nous lors de l'implémentation de ce pattern concerne le type d'arguments du builder, plus précisément sur le fait d'accepter ou non des tableaux de résultats de dés.
Accepter un tableau de valeurs ne permet pas de connaître statiquement le nombre d'éléments fournis. La vérification du nombre d'éléments ne peut se faire qu'au runtime, au moment de l'appel à la méthode *build*.

L'utilisation de ce pattern semble être pertinente dans ce contexte, la rédaction du code gérant les lancers de dés devraient le mettre en évidence.

Son adéquation à un concept métier ne saute pas directement aux yeux : le nom de classe (***YatzyBuilder***) et des méthodes restent techniques, il n'y a pas de notion de Builder dans les règles du Yatzy.
Cependant, on sent que le processus colle fortement avec ce qu'il se passe quand on joue. Un refactoring permettrait peut-être de faire émerger des concepts métier correspondants au builder.

## Disgressions sur les patterns

### Builder

Le pattern **Builder** est certainement le pattern que je rencontre le plus dans mon travail en entreprise.
Assez souvent, un builder est une inner class de la forme suivante:
```java
public class Aaa {

	private final Xxx xxx;
	private final Yyy yyy;
	private final Zzz zzz;

	private Aaa(Xxx xxx, Yyy yyy, Zzz zzz) {
		this.xxx = xxx;
		this.yyy = yyy;
		this.zzz = zzz;
	}

	public class AaaBuilder {

		private Xxx xxx;
		private Yyy yyy;
		private Zzz zzz;

		public AaaBuilder withXxx(Xxx xxx) {
			this.xxx = xxx;
			return this;
		}

		public AaaBuilder withYyy(Yyy yyy) {
			this.yyy = yyy;
			return this;
		}

		public AaaBuilder withZzz(Zzz zzz) {
			this.zzz = zzz;
			return this;
		}

		public Aaa build() {
			checkIfCanBuild();
			return new Aaa(xxx, yyy, zzz);
		}
	}

}
```
Dans cette implémentation, le builder est tout simplement une version mutable de la classe (immutable dans cet exemple, mais pas forcément) qu'il instancie.
Parfois, le builder est utilisé pour instancier une classe à partir de données issues d'un autre contexte. On y retrouve alors des méthodes de ce type:
```java
		public AaaBuilder withXxx(ExternalXxx extXxx) {
			this.xxx = getXxxFrom(extXxx);
			return this;
		}
```
Ces implémentations permettent de découper la collecte des données nécessaires à l'instanciation en étapes distinctes et indépendantes tout en s'assurant que l'objet n'est jamais instancié dans un état non valide.
Cependant, aucune aide syntaxique ne permet de s'assurer de manière statique que tous les arguments obligatoires ont été fournis avant l'appel à la méthode build.

Il est possible de recourir à des interfaces pour s'assurer statiquement que tous les paramètres obligatoires ont été renseignés et guider l'utilisateur. Avec l'exemple précédent, en considérant *zzz* comme une paramètre optionnel.
```java
	public interface AaaEmptyBuilder {
		public AaaWithXxxBuilder withXxx(Xxx xxx);
	}

	public interface AaaWithXxxBuilder {
		public AaaWithXxxAndYyyBuilder withYyy(Yyy yyy);
	}

	public interface AaaWithXxxAndYyyBuilder {
		public AaaWithXxxAndYyyBuilder withZzz(Zzz zzz);
		public Aaa build();
	}

	public class AaaBuilder implements AaaEmptyBuilder, AaaWithXxxBuilder, AaaWithXxxAndYyyBuilder {
	...
	}
```

Ainsi, l'utilisateur ne peut plus appeler la méthode *build* sans avoir auparavant renseigné *xxx* et *yyy*. Un des inconvénients est que l'ordre dans lequel renseigner les paramètres est maintenant fixé (multiplier les interfaces et/ou constructeurs pour corriger ça mène vite à une explosion combinatoire du nombre de classes et d'interfaces).

De manière générale, il me semble que l'objectif généralement recherché est une application partielle du constructeur au travers d'une API "fluent".

Voyons maintenant ce que dit la *bible* au sujet de ce pattern :

*The builder pattern is a design pattern that allows for the step-by-step creation of complex objects using the correct sequence of actions. The construction is controlled by a director object that only needs to know the type of object it is to create.*

![Builder Pattern diagramm](https://raw.githubusercontent.com/cyrilhouillon/Yatzy-Refactoring-Kata/master/img/Builder.png "Builder Pattern diagramm")

