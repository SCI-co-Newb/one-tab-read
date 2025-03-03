import {useState, useEffect} from "react";

export default function Books({user}) {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    async function fetchBooks() {
        setLoading(true);
        setError(null);

        try {
            const response = await fetch(`http://localhost:8080/users/${user.id}/books`)

            if (response.ok) {
                setBooks(await response.json());
            } else {
                console.error("Error fetching books");
            }
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        if (user) {
            (async () => {
                await fetchBooks();
            })();
        }
    }, [user])

    if (!user) return <p>Please log in to view your books</p>;
    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div
            style={{
                textAlign: "center",
                padding: "20px",
                overflowX: "auto",
                whiteSpace: "nowrap",
        }}
        >
            <h1>{user.username}'s Books</h1>
            {books.length > 0 ? (
                <div
                    style={{
                        display: "flex",
                        gap: "20px",
                        justifyContent: "center",
                        padding: "10px",
                    }}
                >
                    {books.map((book) => (
                        <div
                            key={book.id}
                            style={{
                                width: "180px",
                                height: "280px",
                                border: "2px solid #3498db",
                                padding: "15px",
                                borderRadius: "10px",
                                backgroundColor: "#f1f1f1",
                                boxShadow: "3px 3px 10px rgba(0, 0, 0, 0.1)",
                                textAlign: "center",
                                flex: "0 0 auto",
                                display: "flex",
                                flexDirection: "column",
                                justifyContent: "space-between",
                                wordWrap: "break-word",
                                overflowWrap: "break-word",
                            }}
                        >
                            <h3
                                style={{
                                    whiteSpace: "nowrap",
                                    overflow: "hidden",
                                    textOverflow: "ellipsis",
                                    width: "100%",
                                }}
                            >{book.title}</h3>
                        </div>
                    ))}
                </div>
            ) : (
                <p>No books found.</p>
            )
            }
        </div>
    );
}