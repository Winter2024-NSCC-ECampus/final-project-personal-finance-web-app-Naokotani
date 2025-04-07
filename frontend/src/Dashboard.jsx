import { useState, useEffect } from "react";
import axiosInstance from "./axiosInstance";
import { useNavigate } from 'react-router-dom';
import Error from "./Error";
import Layout from "./Layout";
import Cookies from 'js-cookie';

const Dashboard = () => {
  const [error, setError] = useState("");
  const [recurrings, setRecurrings] = useState([]);
  const [showRecurringModal, setShowRecurringModal] = useState(false);
  const [accounts, setAccounts] = useState([]);
  const [accountForm, setAccountForm] = useState({
    name: "",
    type: "",
    balance: 0,
  });
  const [editing, setEditing] = useState(false);
  const [currentAccountId, setCurrentAccountId] = useState(null);
  const [showTransactionForm, setShowTransactionForm] = useState(false);
  const [transactionForm, setTransactionForm] = useState({
    accountId: null,
    description: "",
    transactionDate: "",
    amount: 0,
  });
  const [recurringForm, setRecurringForm] = useState({
    description: "",
    frequency: "",
    amount: 0,
  });
  const [showTransactions, setShowTransactions] = useState(false);
  const [transactions, setTransactions] = useState([]);
  const [currentTransactionId, setCurrentTransactionId] = useState(null);
  const history = useNavigate();
  const cookie = Cookies.get('authToken');

  const fetchAccounts = async () => {
    try {
      const res = await axiosInstance.get("account");
      setAccounts(res.data);
    } catch (e) {
      setError("Error fetching accounts");
      console.log(e);
    }
  };

  const fetchTransactions = async (accountId) => {
    try {
      const res = await axiosInstance.get(`transaction?id=${accountId}`);
      setTransactions(res.data);
      setShowTransactions(true);
      console.log(res.data[0].transactionDate);
      console.log(transactions)
    } catch (e) {
      setError("Error fetching transactions");
      console.log(e);
    }
  };

  const fetchRecurrings = async () => {
    try {
      const res = await axiosInstance.get("recurring");
      setRecurrings(res.data);
    } catch (error) {
      console.error("Error fetching recurrings:", error);
    }
  };

  const createRecurring = async () => {
    try {
      await axiosInstance.post("recurring", recurringForm);
      fetchRecurrings(); // Re-fetch the list of recurrings
      setRecurringForm({ description: "", frequency: "", amount: 0 }); // Reset form
      setShowRecurringModal(false); // Close the modal
    } catch (error) {
      console.error("Error creating recurring expense:", error);
    }
  };



  const createTransaction = async () => {
    try {
      const res = await axiosInstance.post("transaction", transactionForm);
      fetchAccounts(); // Re-fetch accounts to reflect any changes
      setShowTransactionForm(false); // Close the form
      setTransactionForm({ accountId: null, description: "", amount: 0 }); // Reset form
    } catch (e) {
      setError("Error creating transaction");
      console.log(e);
    }
  };

  const handleTransactionClick = (accountId) => {
    setShowTransactionForm(true);
    setTransactionForm({ ...transactionForm, accountId }); // Pre-fill the account ID
  };

  const handleSeeTransactions = (accountId) => {
    fetchTransactions(accountId); // Fetch transactions for this account
  };

  const createAccount = async () => {
    try {
      await axiosInstance.post("account", accountForm);
      fetchAccounts();
      setAccountForm({ name: "", type: "", balance: 0 });
    } catch (e) {
      setError("Error creating account");
      console.log(e);
    }
  };

  const updateAccount = async () => {
    try {
      await axiosInstance.put(`account?id=${currentAccountId}`, accountForm);
      fetchAccounts();
      setAccountForm({ name: "", type: "", balance: 0 });
      setEditing(false);
      setCurrentAccountId(null);
    } catch (e) {
      setError("Error updating account");
      console.log(e);
    }
  };

  const deleteAccount = async (id) => {
    try {
      await axiosInstance.delete(`account?id=${id}`);
      fetchAccounts();
    } catch (e) {
      setError("Error deleting account");
      console.log(e);
    }
  };

  const handleEdit = (account) => {
    setEditing(true);
    setCurrentAccountId(account.id);
    setAccountForm({ name: account.name, type: account.type, balance: account.balance });
  };

  useEffect(() => {
    if (cookie) {
      fetchAccounts();
      fetchRecurrings();
    } else {
      history('/log-in');
    }
  }, []);

  return (
    <Layout>
      <div className="container mt-5">
        {error && <Error erro={error} setError={setError} />}

        {/* Account Form */}
        <div className="box">
          <h2 className="title is-4">{editing ? "Edit Account" : "Create Account"}</h2>
          <form>
            <div className="field">
              <label className="label">Name</label>
              <div className="control">
                <input
                  className="input"
                  type="text"
                  placeholder="Enter account name"
                  value={accountForm.name}
                  onChange={(e) => setAccountForm({ ...accountForm, name: e.target.value })}
                />
              </div>
            </div>

            <div className="field">
              <label className="label">Type</label>
              <div className="control">
                <input
                  className="input"
                  type="text"
                  placeholder="Enter account type"
                  value={accountForm.type}
                  onChange={(e) => setAccountForm({ ...accountForm, type: e.target.value })}
                />
              </div>
            </div>

            <div className="field">
              <label className="label">Balance</label>
              <div className="control">
                <input
                  className="input"
                  type="number"
                  placeholder="Enter account balance"
                  value={accountForm.balance}
                  onChange={(e) => setAccountForm({ ...accountForm, balance: parseFloat(e.target.value) })}
                />
              </div>
            </div>

            <div className="field">
              <div className="control">
                <button
                  type="button"
                  className="button is-primary"
                  onClick={editing ? updateAccount : createAccount}
                >
                  {editing ? "Update Account" : "Create Account"}
                </button>
              </div>
            </div>
          </form>
        </div>

        {/* Accounts List */}
        <div className="box">
          <h2 className="title is-4">Accounts</h2>
          <table className="table is-fullwidth is-bordered is-striped">
            <thead>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Balance</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {accounts.map((account) => (
                <tr key={account.id}>
                  <td>{account.name}</td>
                  <td>{account.type}</td>
                  <td>{account.balance}</td>
                  <td>
                    <button
                      className="button is-warning is-small"
                      onClick={() => handleEdit(account)}
                    >
                      Edit
                    </button>
                    <button
                      className="button is-danger is-small ml-2"
                      onClick={() => deleteAccount(account.id)}
                    >
                      Delete
                    </button>
                    <button
                      className="button is-info is-small ml-2"
                      onClick={() => handleTransactionClick(account.id)}
                    >
                      Create Transaction
                    </button>
                    <button
                      className="button is-link is-small ml-2"
                      onClick={() => handleSeeTransactions(account.id)}
                    >
                      See Transactions
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Transaction Form Modal */}
        {showTransactionForm && (
          <div className="modal is-active">
            <div className="modal-background" onClick={() => setShowTransactionForm(false)}></div>
            <div className="modal-content">
              <div className="box">
                <h2 className="title is-4">Create Transaction</h2>
                <form>
                  <div className="field">
                    <label className="label">Description</label>
                    <div className="control">
                      <input
                        className="input"
                        type="text"
                        placeholder="Enter transaction description"
                        value={transactionForm.description}
                        onChange={(e) => setTransactionForm({ ...transactionForm, description: e.target.value })}
                      />
                    </div>
                  </div>

                  <div className="field">
                    <label className="label">Amount</label>
                    <div className="control">
                      <input
                        className="input"
                        type="number"
                        placeholder="Enter transaction amount"
                        value={transactionForm.amount}
                        onChange={(e) => setTransactionForm({ ...transactionForm, amount: parseFloat(e.target.value) })}
                      />
                    </div>
                  </div>

                  <div className="field">
                    <div className="control">
                      <button
                        type="button"
                        className="button is-primary"
                        onClick={createTransaction}
                      >
                        Create Transaction
                      </button>
                      <button
                        type="button"
                        className="button is-light ml-2"
                        onClick={() => setShowTransactionForm(false)}
                      >
                        Cancel
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
            <button className="modal-close is-large" aria-label="close" onClick={() => setShowTransactionForm(false)}></button>
          </div>
        )}

        {/* Transactions List */}
        {showTransactions && (
          <div className="box mt-5">
            <h2 className="title is-4">Transactions</h2>
            <table className="table is-fullwidth is-bordered is-striped">
              <thead>
                <tr>
                  <th>Description</th>
                  <th>Amount</th>
                  <th>Transaction Date</th>
                </tr>
              </thead>
              <tbody>
                {transactions.map((transaction) => (
                  <tr key={transaction.id}>
                    <td>{transaction.description}</td>
                    <td>{transaction.amount}</td>
                    <td>{new Date(transaction.transactionDate).toLocaleDateString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
        <div>
          {/* Recurring Modal */}
          {showRecurringModal && (
            <div className="modal is-active">
              <div className="modal-background" onClick={() => setShowRecurringModal(false)}></div>
              <div className="modal-content">
                <div className="box">
                  <h2 className="title is-4">Recurring Expenses</h2>
                  <table className="table is-fullwidth is-bordered is-striped">
                    <thead>
                      <tr>
                        <th>Description</th>
                        <th>Frequency</th>
                        <th>Amount</th>
                      </tr>
                    </thead>
                    <tbody>
                      {recurrings.length > 0 ? (
                        recurrings.map((recurring) => (
                          <tr key={recurring.id}>
                            <td>{recurring.description}</td>
                            <td>{recurring.frequency}</td>
                            <td>{recurring.amount}</td>
                          </tr>
                        ))
                      ) : (
                        <tr>
                          <td colSpan="3">No recurring expenses found.</td>
                        </tr>
                      )}
                    </tbody>
                  </table>

                  <h2 className="title is-5">Create a New Recurring Expense</h2>
                  <form>
                    <div className="field">
                      <label className="label">Description</label>
                      <div className="control">
                        <input
                          className="input"
                          type="text"
                          placeholder="Enter description"
                          value={recurringForm.description}
                          onChange={(e) => setRecurringForm({ ...recurringForm, description: e.target.value })}
                        />
                      </div>
                    </div>

                    <div className="field">
                      <label className="label">Frequency</label>
                      <div className="control">
                        <input
                          className="input"
                          type="text"
                          placeholder="Enter frequency (e.g., Monthly)"
                          value={recurringForm.frequency}
                          onChange={(e) => setRecurringForm({ ...recurringForm, frequency: e.target.value })}
                        />
                      </div>
                    </div>

                    <div className="field">
                      <label className="label">Amount</label>
                      <div className="control">
                        <input
                          className="input"
                          type="number"
                          placeholder="Enter amount"
                          value={recurringForm.amount}
                          onChange={(e) => setRecurringForm({ ...recurringForm, amount: parseFloat(e.target.value) })}
                        />
                      </div>
                    </div>

                    <div className="field">
                      <div className="control">
                        <button
                          type="button"
                          className="button is-primary"
                          onClick={createRecurring}
                        >
                          Create Recurring Expense
                        </button>
                        <button
                          type="button"
                          className="button is-light ml-2"
                          onClick={() => setShowRecurringModal(false)}
                        >
                          Cancel
                        </button>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
              <button
                className="modal-close is-large"
                aria-label="close"
                onClick={() => setShowRecurringModal(false)}
              ></button>
            </div>
          )}

          {/* Button to open Recurring Modal */}
          <button
            className="button is-primary"
            onClick={() => setShowRecurringModal(true)}
          >
            View and Create Recurring Expenses
          </button>
        </div>
      </div>
    </Layout>
  );
};

export default Dashboard;
