import React, { useEffect, useState } from "react";
import axios from "axios";

function AddComponent() {
	const [ads, setAds] = useState([]);
	const [isAdVisible, setIsAdVisible] = useState(false);

	const showAdAfterDelay = (delayInSeconds) => {
		setTimeout(() => {
			setIsAdVisible(true);
		}, delayInSeconds * 1000);
	};

	const fetchAllAds = async () => {
		try {
			const response = await axios.get("/api/v1/news/my-ads", {
				withCredentials: true,
				headers: {
					Authorization: "Basic " + btoa("admin:admin")
				}
			});

			if (response.status === 200) {
				const newAds = response.data;
				setAds(newAds);
			} else {
				console.error("Failed to fetch Ad");
			}
		} catch (error) {
			console.error("Error:", error);
		}
	};

	useEffect(() => {
		showAdAfterDelay(20);

		const intervalId = setInterval(() => {
			if (!isAdVisible) {
				showAdAfterDelay(0);
			}
		}, 20000);

		// Fetch new ads after 20 seconds
		const fetchNewAdsInterval = setInterval(() => {
			fetchAllAds();
		}, 20000);

		return () => {
			clearInterval(intervalId);
			clearInterval(fetchNewAdsInterval);
		};
	}, [isAdVisible]);

	const handleHideAd = () => {
		setIsAdVisible(false);
		showAdAfterDelay(20);
	};

	return (
		<div className="container">
			{isAdVisible && (
				<div style={{ position: 'fixed', bottom: '20px', right: '20px', zIndex: 999, maxWidth: '300px', backgroundColor: '#fff', boxShadow: '0 0 10px rgba(0, 0, 0, 0.2)', }} className="ad-panel">
					<button className="close-button" onClick={handleHideAd}>
						X
					</button>
					<div className="py-3">
						<table className="table table-bordered table-striped">
							<thead>
								<tr>
									<th scope="col">Ad Title</th>
									<th scope="col">Ad Content</th>
								</tr>
							</thead>
							<tbody>
								{ads.map((ad, index) => (
									<tr key={index}>
										<td>{ad.title}</td>
										<td>{ad.content}</td>
									</tr>
								))}
							</tbody>
						</table>
					</div>
				</div>
			)}
		</div>
	);
}

export default AddComponent;
