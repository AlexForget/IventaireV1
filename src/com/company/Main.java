package com.company;

/*
	Inventaire
	auteur : Alexandre Forget
	contact : alexandreqc26@gmail.com
	07/06/2021
	Système d'inventaire en ligne de commande. Programmation procédural. Premier travaille d'école.
 */

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner clavier = new Scanner(System.in);
		int dim = 10;											//Pour déterminer la grandeur de l'ensemble des tableaux
		int[] numInv = new int[dim];							//Tableau contenant les numéros d'inventaire d'un vêtement
		int[] quantite = new int[dim];		  					//Tableau contenant les quantité des vêtements en inventaire
		int[] qteMin = new int[dim]; 							//Tableau contenant les quantité minimum à maintenir en inventaire pour un vêtement
		int[] fournisseur = new int[dim];						//Tableau contenant les numéros de fournisseurs. 0 == fabriqué par
																	// l'entreprise, >0 == numéro de fournisseurs
		int idx;												//Indice pour les tableaux et boucles
		int ctrSuppression = 0;									//compteur utiliser pour la recherche et la suppression d'un vêtement (choix 3 du
																	//menu principal)
		int	ctr = 8;											//compteur pour les indices de tableaux (initialiser à 8 a cause des 8
																	//vêtements en "hardcode")
		int choixMenu;  										//Pour permettre à l'utilisateur de faire un choix dans les menus
		int choixGenre;											//Pour choisir le genre du vêtements dans le menu ajout de vêtement
		int	rechercheNumInv;									//Pour faire une recherche par # d'inventaire dans les menu de modifications ou de
																	//suppression
		float[] prixRevient = new float[dim]; 					//Tableau contenant les prix de revient. soit prix d'achat ou de fabrication
		float[] prixVente = new float[dim]; 					//Tableau contenant les prix de vente
		float valeurInvPrixRevient;								//Valeur de l'inventaire total selon le prix de revient
		float valeurInvPrixVente; 								//Valeur de l'inventaire total selon le prix de vente
		boolean[] statut = new boolean[dim]; 					//Tableau contenant les statut des vêtements 0=actif  1=inactif
		boolean finProg = false;								//Pour mettre fin au programme
		boolean validationNumInv = false;						//Pour vérifier que le numéro d'inventaire n'est pas déjà utilisé lors de la
																	//création d'un nouveau vêtement à l'inventaire
		boolean retourMenuPrin = false;							//Pour retourner au menu principal à partir d'un sous menu
		String[] genre = new String[dim];						//Tableau contenant les genres des vêtements. Homme, femme ou unisex
		String[] description = new String[dim]; 				//Tableau contenant les descriptions des vêtements
		String erreurSelection = "Sélection invalide, choisissez de nouveau : ";	//Pour afficher un message lors d'une sélection non valide dans
																	// les différents menu



		//<editor-fold desc="Inventaire entré manuellement pour tester le programme">
		numInv[0] = 1001;
		numInv[1] = 1002;
		numInv[2] = 1003;
		numInv[3] = 1004;
		numInv[4] = 1005;
		numInv[5] = 1006;
		numInv[6] = 1007;
		numInv[7] = 1008;
		quantite[0] = 10;
		quantite[1] = 20;
		quantite[2] = 15;
		quantite[3] = 15;
		quantite[4] = 40;
		quantite[5] = 35;
		quantite[6] = 5;
		quantite[7] = 0;
		qteMin[0] = 15;
		qteMin[1] = 10;
		qteMin[2] = 10;
		qteMin[3] = 20;
		qteMin[4] = 10;
		qteMin[5] = 40;
		qteMin[6] = 15;
		qteMin[7] = 10;
		genre[0] = "homme  ";
		genre[1] = "unisexe";
		genre[2] = "femme  ";
		genre[3] = "femme  ";
		genre[4] = "homme  ";
		genre[5] = "homme  ";
		genre[6] = "femme  ";
		genre[7] = "unisexe";
		prixRevient[0] = 25.5F;
		prixRevient[1] = 15.5F;
		prixRevient[2] = 20.5F;
		prixRevient[3] = 20;
		prixRevient[4] = 25;
		prixRevient[5] = 20;
		prixRevient[6] = 40;
		prixRevient[7] = 10;
		prixVente[0] = 30.5F;
		prixVente[1] = 25.5F;
		prixVente[2] = 25.5F;
		prixVente[3] = 30;
		prixVente[4] = 30;
		prixVente[5] = 25;
		prixVente[6] = 50;
		prixVente[7] = 25;
		description[0] = "chandail";
		description[1] = "tuque";
		description[2] = "jupe longue";
		description[3] = "pantalon";
		description[4] = "veste";
		description[5] = "pantalon";
		description[6] = "chemise";
		description[7] = "gant";
		statut[0] = true;
		statut[1] = true;
		statut[2] = true;
		statut[3] = false;
		statut[4] = true;
		statut[5] = false;
		statut[6] = true;
		statut[7] = true;
		fournisseur[0] = 0;
		fournisseur[1] = 0;
		fournisseur[2] = 1;
		fournisseur[3] = 2;
		fournisseur[4] = 0;
		fournisseur[5] = 0;
		fournisseur[6] = 2;
		fournisseur[7] = 2;
		//</editor-fold


		// Boucle "while" qui contrôle la fermeture du programme avec le boolean "finProg"
		while (!finProg) {

			System.out.println("\n\nChoisissez l'opération désirée : ");
			System.out.println("1. Ajouter un vêtement  ");
			System.out.println("2. Modifier les attributs d'un vêtements  ");
			System.out.println("3. Supprimer / rendre inactif un vêtement  ");
			System.out.println("4. Afficher les listes d'inventaire  ");
			System.out.println("5. Valeur de l'inventaire ");
			System.out.println("0. Fermer le programme ");
			choixMenu = Integer.decode(clavier.nextLine());

			//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
			while ((choixMenu > 5 || choixMenu < 0)) {
				System.out.println(erreurSelection);
				choixMenu = Integer.decode(clavier.nextLine());
			}

			switch (choixMenu) {
				//<editor-fold desc="Choix menu principal 1 pour entrer un nouveau vêtement">
				case 1:
					// Boucle "while" qui permet de valider à la fin de l'entrée de donnés si toutes les informations sont exact et entrer de nouveau
					// les donnés ou retourner au menu principal
					while (!retourMenuPrin) {
						// Boucle "do" dans laquelle on entre le numéro d'inventaire. si jamais il est déjà utilisé (validé avec "boolean"
						// validationNumInv) demande à l'utilisateur d'en entrer un nouveau
						do{
							System.out.println("Entrer le numéro d'inventaire : ");
							rechercheNumInv = Integer.decode(clavier.nextLine());

							// boucle qui fait la recherche du numéro d'inventaire
							for (idx = 0;idx < ctr ;idx++){
								if (rechercheNumInv == numInv[idx]){
									validationNumInv = true;
								}
							}
							//Si le numéro d'inventaire existe déjà (valider avec boolean "validationNumInv") Affiche un message à
							// l'utilisateur et retourne au début de la boucle "do"
							if (validationNumInv){
								System.out.println("Le numéro d'inventaire est déjà utilisé. \n");
								validationNumInv = false;
								// Si le numéro d'inventaire inscrit par l'utilisateur n'a pas été retrouvé dans ceux existent, assigne ce numéro à
								// la prochaine indice du tableau "numInv" et change le boolean "validationNumInv" à "true" pour permettre de sortir
								// de la boucle "do"
							}else {
								numInv[ctr] = rechercheNumInv;
								validationNumInv = true;
							}
							//Fin boucle "do" pour valider la disponibilité du # d'inventaire
						} while (!validationNumInv);
						//Remet le boolean "validationNumInv" à faux pour pouvoir entrer de nouveau dans le boucle lors d'un prochain ajout de
						// vêtement
						validationNumInv = false;

						//Demande à l'utilisateur d'entrer les différents attributs du vêtement ajouté
						System.out.println("Entrer la quantité en inventaire : ");
						quantite[ctr] = Integer.decode(clavier.nextLine());
						System.out.println("Entrer la quantité minimum à maintenir en inventaire : ");
						qteMin[ctr] = Integer.decode(clavier.nextLine());
						System.out.println("Entrer le numéro de fournisseurs : ");
						fournisseur[ctr] = Integer.decode(clavier.nextLine());
						System.out.println("Entrer le prix de revient : ");
						prixRevient[ctr] = clavier.nextFloat();
						System.out.println("Entrer le prix de vente : ");
						prixVente[ctr] = clavier.nextFloat();
						System.out.println("Choisissez le genre du vêtement : \n1. homme \n2. femme \n3. Unisexe ");
						choixGenre = clavier.nextInt();
						clavier.nextLine();

						//Demande à l'utilisateur d'entrer un nouveau choix du genre du vêtement en cas de choix invalide
						while (choixGenre < 1 || choixGenre > 3) {
							System.out.println(erreurSelection);
							choixGenre = Integer.decode(clavier.nextLine());
						}
						//La sélection du genre se fait par choix offert à l'utilisateur pour éviter des erreurs dans les mots saisis.
						if (choixGenre == 1) {
							genre[ctr] = "homme  ";
						}
						if (choixGenre == 2) {
							genre[ctr] = "femme  ";
						}
						if (choixGenre == 3) {
							genre[ctr] = "unisexe";
						}
						System.out.println("Entrer la description du vêtement : ");
						description[ctr] = clavier.nextLine();
						statut[ctr] = true;

						//Affiche les attributs entré et demande à l'utilisateur de valider.
						System.out.println("# inv : " + numInv[ctr] + " | Qte : " + quantite[ctr] + " | Qte min : " + qteMin[ctr] + " | # Fourn" +
								" : " + fournisseur[ctr] + " | Prix revient : " + prixRevient[ctr] + " | Prix vente : " + prixVente[ctr] +
								" | Genre : " + genre[ctr] + " | Desc : " + description[ctr] + "\n");
						System.out.println("Est-ce que toutes les informations sont Exact? \n1. Oui, ajouter le vêtement \n2. Non, entrer" +
								" de nouveaux les informations \n0. Non, retourner au menu principal ");
						choixMenu = Integer.decode(clavier.nextLine());

						//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
						while (choixMenu < 0 || choixMenu > 2) {
							System.out.println(erreurSelection);
							choixMenu = Integer.decode(clavier.nextLine());
						}
						switch (choixMenu) {
							//Ajouter le vêtement, permet de retourner au menu principal et fait avancer le compteur principal
							case 1:
								retourMenuPrin = true;
								ctr++;
								break;
							//Reste dans la loop "while" retourMenuPrin ce qui permet d'entrer de nouveau les information si il y a eu
							// une erreur
							case 2:
								break;
							//Permet de retourner au menu principal sans faire avancer le compteur, donc les informations ne seront pas
							// affiché dans les listes ou calculé dans la valeur et seront effacé lors de l'ajout du prochain vêtement
							case 0:
								retourMenuPrin = true;
								break;
						}
					}
					//Remet le boolean "retourMenuPrin" à faux pour pouvoir entrer de nouveau dans la boucle à partir du menu principal
					retourMenuPrin = false;
					break;
				//</editor-fold>

				//<editor-fold desc="Choix menu principal 2 pour modifier les attributs d'un vêtement">
				case 2:
					System.out.println("\nEntrer le numéro d'inventaire du vêtement auquel vous voulez apporter des modifications :");
					rechercheNumInv = Integer.decode(clavier.nextLine());
					idx = 0;
					// Boucle while qui cherche si le numéro d'inventaire entré par l'utilisateur existe.
					while (rechercheNumInv != numInv[idx] && idx < ctr){
						idx++;
					}
					// Si un # d'inventaire existe cela affiche les attributs du vêtement et demande confirmation que c'est le bon
					if (rechercheNumInv == numInv[idx]) {
						System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] + " | # Fourn : " +
								fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " + prixVente[idx] + " | Genre : " +
								genre[idx] + " | Desc : " + description[idx] + "\n\n" +
								"Est-ce le bon vêtement à modifier? \n1. Oui\n2. Non");
						choixMenu = Integer.decode(clavier.nextLine());

						//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
						while (choixMenu < 1 || choixMenu > 2) {
							System.out.println(erreurSelection);
							choixMenu = Integer.decode(clavier.nextLine());
						}
						// Si le vêtement est bon, demande ce que l'utilisateur veut modifier
						if (choixMenu == 1) {
							System.out.println("\nQue voulez-vous modifier : \n1. Quantité en inventaire \n2. Prix de revient \n" +
									"3. Prix de vente \n4. Le statut");
							choixMenu = Integer.decode(clavier.nextLine());

							//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
							while ((choixMenu < 1) || (choixMenu > 8)) {
								System.out.println(erreurSelection);
								choixMenu = Integer.decode(clavier.nextLine());
							}
							//Switch qui selon le choix de l'utilisateur présente l'attribut à modifier et enregistre le nouvel attribut le remplacent
							switch (choixMenu) {
								//choix pour modifier le quantité en inventaire
								case 1:
									System.out.println("La quantité en inventaire actuelle est " + quantite[idx] + ", entrer la nouvelle quantité " +
											"en inventaire : ");
									quantite[idx] = Integer.decode(clavier.nextLine());
									System.out.println("\nVoici le vêtement numéro " + numInv[idx] + " avec la modification apporté :\n# inv : " +
											numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] + " | # Fourn : " +
											fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " + prixVente[idx] +
											" | Genre : " + genre[idx] + " | Desc : " + description[idx]);
									break;

								//choix pour modifier le prix de revient
								case 2:
									System.out.println("Le prix de revient actuel est " + prixRevient[idx] + ", entrer le nouveau prix de revient " +
											":e ");
									prixRevient[idx] = clavier.nextFloat();
									System.out.println("\nVoici le vêtement numéro " + numInv[idx] + " avec la modification apporté :\n# inv : " +
											numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] + " | # Fourn : " +
											fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " + prixVente[idx] +
											" | Genre : " + genre[idx] + " | Desc : " + description[idx]);
									clavier.nextLine();
									break;

								//choix pour modifier le prix de vente
								case 3:
									System.out.println("Le prix de vente actuel est " + prixVente[idx] + ", entrer le nouveau prix de vente : ");
									prixVente[idx] = clavier.nextFloat();
									System.out.println("\nVoici le vêtement numéro " + numInv[idx] + " avec la modification apporté :\n# inv : " +
											numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] + " | # Fourn : " +
											fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " + prixVente[idx] +
											" | Genre : " + genre[idx] + " | Desc : " + description[idx]);
									clavier.nextLine();
									break;

								//choix pour modifier le statut du vêtement
								case 4:
									// Pour empêcher l'utilisateur d'activer un vêtement déjà actif
									if (statut[idx]) {
										System.out.println("Ce vêtement est déjà actif. Pour le rendre inactif sélectionner l'option 3. Supprimer " +
												"/ rendre inactif un vêtement dans le menu principal");

										// Permet de changer le statut si le vêtement est inactif
									} else {
										System.out.println("\nCe vêtement est actuellement inactif, voulez-vous l'activer? \n1. Oui \n2. Non");
										choixMenu = Integer.decode(clavier.nextLine());
										System.out.println("Le vêtement numéro " + numInv[idx] + " a été activé dans l'inventaire.");

										//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
										while ((choixMenu < 1) || (choixMenu > 2)) {
											System.out.println(erreurSelection);
											choixMenu = Integer.decode(clavier.nextLine());
										}
										// Confirmation pour modifier le statut du vêtement
										switch (choixMenu) {
											case 1:
												statut[idx] = true;
												break;
											case 2:
												System.out.println("La statut reste actif.");
												break;
										}
									}
									break;
								default:
							}
						}
					}else{  //fin du if qui recherche le numéro d'inventaire. Affiche un message si jamais le # est invalide
						System.out.println("Le numéro d'inventaire est invalide.");
					}
					break;
				//</editor-fold>

				//<editor-fold desc="Choix menu principal 3 pour supprimer un vêtement ou le rendre inactif">
				case 3:
					System.out.println("\nEntrer le numéro d'inventaire du vêtement que vous voulez supprimer / mettre inactif :");
					rechercheNumInv = Integer.decode(clavier.nextLine());

					//Fait une recherche par # d'inventaire pour l'item à supprimer. Un nouveau compteur est utilisé pour faire la recherche à
					// partir de l'indice zéro du tableau sans affecter le compteur principal.
					while ((rechercheNumInv != numInv[ctrSuppression]) && (ctrSuppression < ctr)) {
						ctrSuppression++;
					}
					// Si le # d'inventaire est bon présente le vêtement sélectionné et demande une confirmation du vêtement
					if (rechercheNumInv == numInv[ctrSuppression]) {
						System.out.println("# inv : " + numInv[ctrSuppression] + " | Qte : " + quantite[ctrSuppression] + " | Qte min : " +
								qteMin[ctrSuppression] + " | # Fourn : " + fournisseur[ctrSuppression] + " | Prix revient : " +
								prixRevient[ctrSuppression] + " | Prix vente : " + prixVente[ctrSuppression] + " | Genre : " + genre[ctrSuppression] +
								" | Desc : " + description[ctrSuppression] + "\n\n" + "Est-ce le bon vêtement à supprimer / rendre inactif? \n1. " +
								"Oui\n2. Non");
						choixMenu = Integer.decode(clavier.nextLine());
						//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
						while (choixMenu < 1 || choixMenu > 2) {
							System.out.println(erreurSelection);
							choixMenu = Integer.decode(clavier.nextLine());
						}
						//Demande l'action à faire. Supprimer ou rendre le vêtement inactif
						if (choixMenu == 1) {
							System.out.println("\nQue voulez-vous faire : \n1. Supprimer le vêtements (Cette option le supprimera de l'inventaire " +
									"de façons définitive)  \n2. Mettre le vêtement inactif \n");
							choixMenu = Integer.decode(clavier.nextLine());

							//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
							while ((choixMenu < 1) || (choixMenu > 2)) {
								System.out.println(erreurSelection);
								choixMenu = Integer.decode(clavier.nextLine());
							}
							switch (choixMenu) {
								//Supprimer le vêtement - boucle qui recule les indices du tableau pour "vider" celles supprimer
								case 1:
									System.out.println("Le vêtement numéro " + numInv[ctrSuppression] + " a définitivement été supprimé de " +
											"l'inventaire.");
									for (numInv[ctrSuppression] = ctrSuppression; ctrSuppression < ctr; ctrSuppression++) {
										numInv[ctrSuppression] = numInv[ctrSuppression + 1];
										quantite[ctrSuppression] = quantite[ctrSuppression + 1];
										qteMin[ctrSuppression] = qteMin[ctrSuppression + 1];
										fournisseur[ctrSuppression] = fournisseur[ctrSuppression + 1];
										prixRevient[ctrSuppression] = prixRevient[ctrSuppression + 1];
										prixVente[ctrSuppression] = prixVente[ctrSuppression + 1];
										genre[ctrSuppression] = genre[ctrSuppression + 1];
										description[ctrSuppression] = description[ctrSuppression + 1];
										statut[ctrSuppression] = statut[ctrSuppression + 1];
									}
									// Pour reculer le compteur principal pour éviter de laisser une indice vide et ajuster les recherche et
									// modifications qui dependent du compteur
									ctr--;
									break;
								// 	Pour rendre le vêtement sélectionné inactif
								case 2:
									//Condition pour valider si le vêtement est déjà inactif et ne pas le désactiver si c'est le cas.
									if (!statut[ctrSuppression]){
										System.out.println("Le vêtement numéro " + numInv[ctrSuppression] + " ne peut pas être désactivé, il est " +
												"déjà inactif.");
									}else {
										System.out.println("Le vêtement numéro " + numInv[ctrSuppression] + " est maintenant inactif.");
										statut[ctrSuppression] = false;
									}
									break;
								default:
							}
						}
					}   //fin du if qui recherche le numéro d'inventaire
					else {
						System.out.println("Le numéro d'inventaire est invalide.");
					}
					//remet le compteur de suppression à zéro pour permettre de supprimer un nouvel item.
					ctrSuppression = 0;
					break;
				//</editor-fold>

				//<editor-fold desc="Choix menu principal 4 pour afficher les différents choix de liste d'inventaire à afficher">
				case 4:
					// Boolean qui permet d'offrir le choix d'afficher une autre liste d'inventaire après en avoir afficher une
					retourMenuPrin = false;
					while (!retourMenuPrin) {
						System.out.println("\nChoisir la liste d'inventaire à afficher : ");
						System.out.println("1. Liste des vêtements actifs ");
						System.out.println("2. Liste des vêtements inactifs ");
						System.out.println("3. Liste des vêtements pour hommes ");
						System.out.println("4. Liste des vêtements pour femmes ");
						System.out.println("5. Liste des vêtements unisexes ");
						System.out.println("6. Liste des vêtements à commander ");
						System.out.println("7. Liste des vêtements fabriqués ");
						System.out.println("8. Liste des vêtements provenant d'un fournisseurs ");
						System.out.println("0. Retourner au menu principal ");
						choixMenu = Integer.decode(clavier.nextLine());

						//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
						while ((choixMenu > 8 || choixMenu < 0)) {
							System.out.println(erreurSelection);
							choixMenu = Integer.decode(clavier.nextLine());
						}
						//"switch" pour la sélection des différentes listes d'inventaires
						switch (choixMenu) {

							//Liste d'inventaire 1: Vêtement actifs
							case 1:
								for (idx = 0; idx < ctr; idx++) {

									//condition pour le statut du vêtement, doit être actif et les affiches
									if (statut[idx]) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println("----------------------------------------------------------------------------------" +
												"--------------------------------------------------------------");
									}
								}
								System.out.println("\n1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());

								//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
								while ((choixMenu < 0) || (choixMenu > 1)) {
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 2: Vêtement inactifs
							case 2:
								for (idx = 0; idx < ctr; idx++) {

									//condition pour le statut du vêtement, doit être inactif et les affiches
									if (!statut[idx]) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println("---------------------------------------------------------------------------------------" +
												"---------------------------------------------------------");
									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());

								//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
								while ((choixMenu < 0) || (choixMenu > 1)) {
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 3: Vêtement pour homme
							case 3:
								for (idx = 0; idx < ctr; idx++) {

									//condition pour le genre du vêtement, doit être pour homme et les affiches
									if ((genre[idx].equals("homme  ")) && (statut[idx])) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println("--------------------------------------------------------------------------------------" +
												"----------------------------------------------------------");
									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());

								//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
								while ((choixMenu < 0) || (choixMenu > 1)) {
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 4: Vêtement pour femme
							case 4:
								for (idx = 0; idx < ctr; idx++) {

									//condition pour le genre du vêtement, doit être pour femme et les affiches
									if ((genre[idx].equals("femme  ")) && (statut[idx])) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println("-------------------------------------------------------------------------------------" +
												"-----------------------------------------------------------");
									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());

								//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
								while ((choixMenu < 0) || (choixMenu > 1)) {
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 5: Vêtement unisexe
							case 5:
								for (idx = 0; idx < ctr; idx++) {

									//condition pour le genre du vêtement, doit être unisexe et les affiches
									if ((genre[idx].equals("unisexe")) && (statut[idx])) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println("--------------------------------------------------------------------------------------" +
												"----------------------------------------------------------");
									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());

								//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
								while ((choixMenu < 0) || (choixMenu > 1)) {
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 6: Vêtement à commander
							case 6:
								for (idx = 0; idx < ctr; idx++) {

									//Condition qui fait la différence entre les variable en stock et minimum en stock pour afficher ceux en
									// déficit. Une colonne de plus à la liste pour afficher la quantité déficitaire
									if ((quantite[idx] - qteMin[idx] <= 0) && (statut[idx])) {
										System.out.println("# inv : " + numInv[idx] + " | Qte déficitaire : " + (qteMin[idx] - quantite[idx]) + " |" +
												" Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] + " | # Fourn : " + fournisseur[idx] +
												" | Prix revient : " + prixRevient[idx] + " | Prix vente : " + prixVente[idx] + " | Genre : " +
												genre[idx] + " | Desc : " + description[idx]);
										System.out.println(
												"-------------------------------------------------------------------------------------------------" +
														"-------------------------------------------------------");
									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());

								//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
								while ((choixMenu < 0) || (choixMenu > 1)) {
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 7: liste des vêtements fabriqués par l'entreprise
							case 7:
								for (idx = 0; idx < ctr; idx++) {

									//Condition qui affiche les vêtements qui ont comme numéro de fournisseur 0 == fabriqué et non acheté et
									// les affiches
									if ((fournisseur[idx] == 0) && (statut[idx])) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println(
												"---------------------------------------------------------------------------------------------" +
														"---------------------------------------------------");
									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());
								while ((choixMenu < 0) || (choixMenu > 1)) {

									//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							//Liste d'inventaire 8: liste des vêtements achetés chez un fournisseurs
							case 8:
								for (idx = 0; idx < ctr; idx++) {

									//Condition qui affiche les vêtements qui ont comme numéro de fournisseur >0 == acheté chez un fournisseurs et
									// les affiches
									if ((fournisseur[idx] > 0) && (statut[idx])) {
										System.out.println("# inv : " + numInv[idx] + " | Qte : " + quantite[idx] + " | Qte min : " + qteMin[idx] +
												" | # Fourn : " + fournisseur[idx] + " | Prix revient : " + prixRevient[idx] + " | Prix vente : " +
												prixVente[idx] + " | Genre : " + genre[idx] + " | Desc : " + description[idx]);
										System.out.println(
												"----------------------------------------------------------------------------------------------" +
														"--------------------------------------------------");

									}
								}
								System.out.println("1. Afficher une autre liste d'inventaire \n0. Retourner au menu principal");
								choixMenu = Integer.decode(clavier.nextLine());
								while ((choixMenu < 0) || (choixMenu > 1)) {

									//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
									System.out.println(erreurSelection);
									choixMenu = Integer.decode(clavier.nextLine());
								}
								if (choixMenu == 0) {
									retourMenuPrin = true;
								}
								break;
							case 0:
								retourMenuPrin = true;
						}
					}
					retourMenuPrin = false;
					//fin du "switch" pour le choix des listes d'inventaires
					break;
				//</editor-fold>

				//<editor-fold desc="Choix menu principal 5 pour afficher la valeur de l'inventaire selon le prix de vente">
				case 5:
					//Boucle "while" permettant de retourner au menu principal dans le sous menu des valeurs d'inventaire
					while (!retourMenuPrin) {
						//Affiche et demande le choix de l'utilisateur au menu des valeurs d'inventaire
						System.out.println("\nEn fonction de quoi voulez-vous consulter la valeur de l'inventaire : \n1. Le prix de revient \n2. Le" +
								" prix de vente \n0. Retour au menu principal");
						choixMenu = Integer.decode(clavier.nextLine());
						//Demande à l'utilisateur d'entrer un nouveau choix en cas de choix invalide
						while ((choixMenu > 3 || choixMenu < 0)) {
							System.out.println(erreurSelection);
							choixMenu = Integer.decode(clavier.nextLine());
						}
						//Switch pour afficher le choix de l'utilisateur
						switch (choixMenu) {
							case 1:
								//Remet la valeur de la variable "valeurInvPrixRevient" à zéro pour éviter qu'elle soit additionner plusieurs fois
								//si l'utilisateur l'affiche plus d'une fois.
								valeurInvPrixRevient = 0;
								//Boucle for qui additionne les valeurs de revient et l'affiche
								for (idx = 0; idx < ctr; idx++) {
									valeurInvPrixRevient = valeurInvPrixRevient + (prixRevient[idx] * quantite[idx]);
								}
								System.out.print("\nLa valeur de l'inventaire selon le prix de revient est de : " + valeurInvPrixRevient + "\n");
								break;

							case 2:
								//Remet la valeur de la variable "valeurInvPrixVente" à zéro pour éviter qu'elle soit additionner plusieurs fois
								// si l'utilisateur l'affiche plus d'une fois.
								valeurInvPrixVente = 0;
								//Boucle for qui additionne les valeurs de vente et l'affiche
								for (idx = 0; idx < ctr; idx++) {
									valeurInvPrixVente = valeurInvPrixVente + (prixVente[idx] * quantite[idx]);
								}
								System.out.print("\nLa valeur de l'inventaire selon le prix de vente est de : " + valeurInvPrixVente + "\n");
								break;
							case 0:
								retourMenuPrin = true;
						}
					}
					retourMenuPrin = false;
					//fin du switch pour le sous menu des valeurs d'inventaire
					break;

				//</editor-fold>

				//<editor-fold desc="Choix menu principal 0 pour fermer le programme">
				case 0:
					finProg = true;
					//</editor-fold>

					//fin du "switch" pour les choix du menu principal
					break;
			}
		}

	}
}
