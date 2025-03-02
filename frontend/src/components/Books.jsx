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
        <div>
            <h1>{user.username}'s Books</h1>
            {books.length > 0 ? (
                <ul>
                    {books.map((book) => (
                        <li key={book.id}>{book.title}</li>
                    ))}
                </ul>
            ) : (
                <p>No books found.</p>
            )
            }
        </div>
    );

}