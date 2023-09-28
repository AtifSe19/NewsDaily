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
				const ads = response.data;
				setAds(ads);
			} else {
				console.error("Failed to fetch Add");
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

		fetchAllAds();
		
		return () => clearInterval(intervalId);
		
	}, [isAdVisible]);

	const handleHideAd = () => {
		setIsAdVisible(false);
		showAdAfterDelay(20);
	};

	return (
		<div className="container">
			{isAdVisible && (
				<div className="ad-panel">
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
