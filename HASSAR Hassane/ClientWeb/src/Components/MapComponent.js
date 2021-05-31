/*global google*/
import React, { Component } from 'react';
import { GMap } from 'primereact/gmap';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Dropdown } from 'primereact/dropdown';
import { Card } from 'primereact/card';
import UserService from '../Services/UserService';
import PositionService from '../Services/PositionService';

export default class MapComponent extends Component {
	constructor() {
		super();
		this.state = {
			googleMapsIsReady: false,
			markerTitle: '',
			draggableMarker: false,
			overlays: null,
			selectedPosition: null,
			userId: 0,
			users: [],
			dateDebut: '',
			dateFin: '',
			trajet: [],
			options: {
				center: { lat: 34.36980065299986, lng: -7.9515926526158715 },
				zoom: 6
			}
		};

		this.onMapClick = this.onMapClick.bind(this);
		this.handleDragEnd = this.handleDragEnd.bind(this);
		this.onCancel = this.onCancel.bind(this);
		this.loadPath = this.loadPath.bind(this);
		this.onOverlayClick = this.onOverlayClick.bind(this);
	}

	componentWillMount() {
		loadGoogleMaps(() => {
			this.setState({ googleMapsIsReady: true });
			this.loadUsers();
		});
	}

	onMapClick(event) {
		this.setState({
			selectedPosition: event.latLng
		});
	}

	loadUsers() {
		const userService = new UserService();
		userService.getAllUsers().then(data => {
			var user = [];
			for (let i = 0; i < data.data.length; i++) {
				user.push({ label: data.data[i].nom + " " + data.data[i].prenom, value: data.data[i].id })
			}
			this.setState({ users: user });
		});
	}

	onOverlayClick(event) {
		let isMarker = event.overlay.getTitle !== undefined;
		if (isMarker) {
			let title = event.overlay.getTitle();
			this.infoWindow = this.infoWindow || new google.maps.InfoWindow();
			this.infoWindow.setContent('<div>' + title + '</div>');
			this.infoWindow.open(event.map, event.overlay);
			event.map.setCenter(event.overlay.getPosition());
		}
	}

	handleDragEnd(event) {
		this.growl.show({ severity: 'info', summary: 'Marker Dragged', detail: event.overlay.getTitle() });
	}

	loadPath() {
		const positionService = new PositionService();
		positionService.getAllpositions(this.state.userId, this.state.dateDebut, this.state.dateFin).then(data => {
			var trajet = [];
			for (let i = 0; i < data.data.length; i++) {
				trajet.push({ lat: data.data[i].latitude, lng: data.data[i].longitude })
			}
			this.setState({
				overlays: [
					new google.maps.Polyline({ path: trajet, geodesic: true, strokeColor: '#FF0000', strokeOpacity: 1, strokeWeight: 10 }),
					new google.maps.Marker({ position: trajet[0], title: "Départ" }),
					new google.maps.Marker({ position: trajet[trajet.length - 1], title: "Arrivée" })
				],
				dialogVisible: false,
				options: { center: trajet[0], zoom: 12 }

			});
		});
		return;
	}

	onCancel(event) {
		this.setState({ userId: "", dateDebut: "", dateFin: "" });
	}

	scriptIsLoad() {
		setTimeout(() => {
			this.setState({ scriptIsLoad: true });
		}, 2000);
	}

	render() {

		if (this.state.googleMapsIsReady === false) {
			return '';
		}
		return (
			<div style={{ position:"relative" }}>
				<Card style={{ width: '20rem', top: '15%',marginLeft:20,position:"absolute",zIndex: '1' }}>
					<div className="p-col-2" style={{ textAlign:'left',marginLeft:25,fontWeight:'bold' }}><label htmlFor="utilisateur">Utilisateur :</label></div>
					<div className="p-col-10"><Dropdown style={{ width: '90%',textAlign:"start" }} value={this.state.userId} id="utilisateur" options={this.state.users} onChange={(e) => this.setState({ userId: e.target.value })} placeholder="Choisissez un utilisateur" /></div>

					<div className="p-col-2" style={{ paddingTop: '.75em',textAlign:'left',marginLeft:25,fontWeight:'bold' }}><label htmlFor="dateDebut">Date de début :</label></div>
					<div className="p-col-10"><InputText style={{ width: '90%' }} type="date" value={this.state.dateDebut} onChange={(e) => this.setState({ dateDebut: e.target.value })} /></div>

					<div className="p-col-2" style={{ paddingTop: '.75em',textAlign:'left',marginLeft:25,fontWeight:'bold' }}><label htmlFor="dateFin">Date de fin :</label></div>
					<div className="p-col-10"><InputText style={{ width: '90%' }} type="date" value={this.state.dateFin} onChange={(e) => this.setState({ dateFin: e.target.value })} /></div>

					<Button style={{ margin: 10 }} label="Chercher" icon="pi pi-search" onClick={this.loadPath} />
					<Button style={{ margin: 10 }} label="Annuler" onClick={this.onCancel} />

					</Card>
				<div>
					<GMap overlays={this.state.overlays} options={this.state.options} style={{ width: '100%', minHeight: '600px' }} onMapReady={this.onMapReady}
						onMapClick={this.onMapClick} onOverlayClick={this.onOverlayClick} />
				</div>
			</div>

		);
	}
}

const loadGoogleMaps = (callback) => {
	const existingScript = document.getElementById('googleMaps');

	if (!existingScript) {
		const script = document.createElement('script');
		script.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBFVIXR7E5_b2eKoWql0_wj5Z7jxnVFLYg&libraries=places';
		script.id = 'googleMaps';
		document.body.appendChild(script);

		script.onload = () => {
			if (callback) callback();
		};
	}

	if (existingScript && callback) callback();
};